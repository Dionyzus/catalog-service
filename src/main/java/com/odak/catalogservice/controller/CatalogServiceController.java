package com.odak.catalogservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.CatalogItemRepository;

@RestController
@RequestMapping("api/v1")
public class CatalogServiceController {
	
	private CatalogItemRepository catalogItemRepository;
	
	@Autowired
	public CatalogServiceController(CatalogItemRepository catalogItemRepository) {
		this.catalogItemRepository = catalogItemRepository;
	}

	@GetMapping("/catalog-services")
	public List<CatalogItem> getAllCatalogItems() {
		return catalogItemRepository.getAllCatalogItems();
	}
}
