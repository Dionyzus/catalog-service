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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.service.CatalogItemsService;

@RestController
@RequestMapping("api/v1")
public class CatalogItemController {

	private CatalogItemsService catalogItemsService;

	@Autowired
	public CatalogItemController(CatalogItemsService catalogItemsService) {
		this.catalogItemsService = catalogItemsService;
	}

	@PostMapping("/catalog-items")
	public ResponseEntity<CatalogItem> createCatalogItem(@Validated @RequestBody CatalogItem catalogItemDetails) {

		CatalogItem catalogItem = catalogItemsService.create(catalogItemDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(catalogItem);
	}

	@GetMapping("/catalog-items")
	public ResponseEntity<Page<CatalogItem>> getCatalogItems(@RequestParam HashMap<String, String> queryParams) throws BadRequestException {

		Page<CatalogItem> catalogItems = catalogItemsService.query(queryParams);

		return ResponseEntity.ok(catalogItems);
	}

	@GetMapping("/catalog-items/{id}")
	public ResponseEntity<CatalogItem> getCatalogItemById(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemsService.getCatalogItemById(itemId);

		return ResponseEntity.ok(catalogItem);
	}

	@PutMapping("/catalog-items/{id}")
	public ResponseEntity<CatalogItem> updateCatalogItem(@PathVariable(value = "id") String itemId,
			@RequestBody CatalogItem catalogItemDetails) throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemsService.update(itemId, catalogItemDetails);

		return ResponseEntity.ok(catalogItem);
	}

	@DeleteMapping("/catalog-items/{id}")
	public ResponseEntity<CatalogItem> deleteCatalogItem(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		catalogItemsService.delete(itemId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
