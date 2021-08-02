package com.odak.catalogservice.controller.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.JsonNode;
import com.odak.catalogservice.controller.JsonUtil;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.model.Category;

public class CatalogItemControllerTest extends AbstractTest {

	CatalogItem catalogItem;

	@Override
	@Before
	public void setUp() {
		super.setUp();
		catalogItem = new CatalogItem();
		catalogItem.setId("1");
		catalogItem.setName("New test item");
		catalogItem.setDescription("Test description");
		catalogItem.setPrice(10.5);
		catalogItem.setCategories(List.of(new Category("40", "Test category")));
	}

	@Test
	public void getCatalogItems() throws Exception {
		String uri = "/api/v1/catalog-items";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();

		JsonNode jsonNode = JsonUtil.parseToTree(content).get("content");
		CatalogItem[] catalogItems = JsonUtil.mapFromJson(jsonNode.toString(), CatalogItem[].class);
		assertTrue(catalogItems.length > 0);
	}

	@Test
	public void createCatalogItem() throws Exception {
		String uri = "/api/v1/catalog-items";

		String inputJson = JsonUtil.toJsonString(catalogItem);

		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(201, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, inputJson);
	}

	@Test
	public void updateCatalogItem() throws Exception {
		String uri = "/api/v1/catalog-items/1";

		catalogItem.setName("Updated test name");
		String inputJson = JsonUtil.toJsonString(catalogItem);

		MvcResult mvcResult = mvc
				.perform(MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, inputJson);
	}

	@Test
	public void deleteCatalogItem() throws Exception {
		String uri = "/api/v1/catalog-items/1";
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(204, status);
		String content = mvcResult.getResponse().getContentAsString();
		assertEquals(content, "");
	}
}