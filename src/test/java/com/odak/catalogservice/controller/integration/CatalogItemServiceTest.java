package com.odak.catalogservice.controller.integration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.odak.catalogservice.controller.CatalogItemController;
import com.odak.catalogservice.controller.JsonUtil;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.service.CatalogItemService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CatalogItemController.class)
@AutoConfigureMockMvc
public class CatalogItemServiceTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private CatalogItemService service;

	CatalogItem firstItem;
	CatalogItem secondItem;
	List<CatalogItem> catalogItems = new ArrayList<>();

	@Before
	public void setUp() throws Exception {
		firstItem = new CatalogItem();
		firstItem.setId("1");
		firstItem.setName("Test item");
		firstItem.setDescription("Test description");
		firstItem.setPrice(10.5);
		firstItem.setCategories(List.of(new Category("1", "Test category")));

		secondItem = new CatalogItem();
		secondItem.setId("2");
		secondItem.setName("Test item 2");
		secondItem.setDescription("Test description 2");
		secondItem.setPrice(10.5);
		secondItem.setCategories(List.of(new Category("2", "Test category 2")));

		catalogItems = Arrays.asList(firstItem, secondItem);
	}

	@Test
	public void whenPostCatalogItem_thenCreateCatalogItem() throws Exception {
		given(service.create(Mockito.any())).willReturn(firstItem);

		mvc.perform(post("/api/v1/catalog-items").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJsonByteArray(firstItem))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.name", is(firstItem.getName())));
		verify(service, VerificationModeFactory.times(1)).create(Mockito.any());
		reset(service);
	}

	@Test
	public void givenCatalogItems_whenGetCatalogItems_thenReturnJsonArray() throws Exception {

		HashMap<String, String> queryParams = new HashMap<>();
		PageRequest pageable = PageRequest.of(0, 5);

		PageImpl<CatalogItem> page = new PageImpl<CatalogItem>(catalogItems, pageable, catalogItems.size());

		given(service.query(Mockito.any())).willReturn(page);

		mvc.perform(get("/api/v1/catalog-items").contentType(MediaType.APPLICATION_JSON)
				.content(JsonUtil.toJsonString(catalogItems))).andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(2)))
				.andExpect(jsonPath("$.content.[0].name", is(firstItem.getName())))
				.andExpect(jsonPath("$.content.[1].name", is(secondItem.getName())));

		verify(service, VerificationModeFactory.times(1)).query(queryParams);
		reset(service);
	}
}