package com.odak.catalogservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.service.CatalogItemService;

@RestController
@RequestMapping("api/v1")
public class CatalogItemController {

	private CatalogItemService catalogItemService;

	@Autowired
	public CatalogItemController(CatalogItemService catalogItemsService) {
		this.catalogItemService = catalogItemsService;
	}

	@RequestMapping(value = "/catalog-items", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = "application/json")
	public ResponseEntity<CatalogItem> createCatalogItem(@Validated @RequestBody CatalogItem catalogItemDetails) {

		CatalogItem catalogItem = catalogItemService.create(catalogItemDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(catalogItem);
	}

	@RequestMapping(value = "/catalog-items", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<Page<CatalogItem>> getCatalogItems(@RequestParam HashMap<String, String> queryParams)
			throws BadRequestException {

		Page<CatalogItem> catalogItems = catalogItemService.query(queryParams);

		return ResponseEntity.ok(catalogItems);
	}

	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<CatalogItem> getCatalogItemById(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemService.getCatalogItemById(itemId);

		return ResponseEntity.ok(catalogItem);
	}

	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.PUT, produces = {
			"application/json" }, consumes = "application/json")
	public ResponseEntity<CatalogItem> updateCatalogItem(@PathVariable(value = "id") String itemId,
			@RequestBody CatalogItem catalogItemDetails) throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemService.update(itemId, catalogItemDetails);

		return ResponseEntity.ok(catalogItem);
	}

	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CatalogItem> deleteCatalogItem(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		catalogItemService.delete(itemId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
