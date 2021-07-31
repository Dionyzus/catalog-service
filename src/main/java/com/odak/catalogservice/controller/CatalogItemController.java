package com.odak.catalogservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.CatalogItemRepository;
import com.odak.catalogservice.services.SearchService;

@RestController
@RequestMapping("api/v1")
public class CatalogItemController {

	private CatalogItemRepository catalogItemRepository;
	private SearchService searchService;

	@Autowired
	public CatalogItemController(CatalogItemRepository catalogItemRepository, SearchService searchService) {
		this.catalogItemRepository = catalogItemRepository;
		this.searchService = searchService;
	}

	@PostMapping("/catalog-items")
	public ResponseEntity<CatalogItem> createCatalogItem(@Validated @RequestBody CatalogItem catalogItemDetails) {
		CatalogItem catalogItem = catalogItemRepository.save(catalogItemDetails);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(catalogItem);
	}

	@GetMapping("/catalog-items")
	public ResponseEntity<List<CatalogItem>> getAllCatalogItems() {
		List<CatalogItem> allCatalogItems = catalogItemRepository.getAllCatalogItems();

		return ResponseEntity.ok(allCatalogItems);
	}

	@GetMapping("/catalog-items/{name}")
	public ResponseEntity<CatalogItem> getCatalogItemByName(@PathVariable(value = "name") String itemName)
			throws ResourceNotFoundException {
		
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemByName(itemName)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemName));

		return ResponseEntity.ok(catalogItem);
	}
	
	@GetMapping("/catalog-items/search")
	public ResponseEntity<List<CatalogItem>> findCatalogItemsByName(@RequestParam("name") String name) {
		List<CatalogItem> catalogItems = searchService.searchByName(catalogItemRepository.getAllCatalogItems(), name);
		
		return ResponseEntity.ok(catalogItems);
	}
	
	@GetMapping("/catalog-items/text-search")
	public ResponseEntity<List<CatalogItem>> findCatalogItemsText(@RequestParam("text") String text) {
		List<CatalogItem> catalogItems = searchService.searchByText(catalogItemRepository.getAllCatalogItems(), text);
		
		return ResponseEntity.ok(catalogItems);
	}
	
	@GetMapping("/catalog-items/filter")
	public ResponseEntity<List<CatalogItem>> filterCatalogItemsByCategory(@RequestParam("categories") List<String> categories) {
		List<CatalogItem> catalogItems = searchService.searchByCategories(catalogItemRepository.getAllCatalogItems(), categories);
		
		return ResponseEntity.ok(catalogItems);
	}

	@PutMapping("/catalog-items/{name}")
	public ResponseEntity<CatalogItem> updateCatalogItem(@PathVariable(value = "name") String itemName,
			@RequestBody CatalogItem catalogItemDetails) throws ResourceNotFoundException {
		
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemByName(itemName)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemName));

		catalogItemRepository.update(catalogItem, catalogItemDetails);

		return ResponseEntity.ok(catalogItem);
	}

	@DeleteMapping("/catalog-items/{name}")
	public ResponseEntity<CatalogItem> deleteCatalogItem(@PathVariable(value = "name") String itemName)
			throws ResourceNotFoundException {
		catalogItemRepository.getCatalogItemByName(itemName)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemName));

		catalogItemRepository.deleteByName(itemName);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
