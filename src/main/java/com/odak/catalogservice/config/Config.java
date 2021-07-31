package com.odak.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.odak.catalogservice.repository.CatalogItemRepository;
import com.odak.catalogservice.repository.CategoryRepository;
import com.odak.catalogservice.repository.ImageRepository;
import com.odak.catalogservice.service.CatalogItemsService;

@Configuration
@ComponentScan("com.odak.repository")
public class Config {

	@Bean
	public CategoryRepository categoryRepository() {
		return new CategoryRepository();
	}
	
	@Bean
	public ImageRepository imageRepository() {
		return new ImageRepository();
	}
	
	@Bean
	public CatalogItemRepository catalogItemRepository() {
		return new CatalogItemRepository();
	}
	
	@Bean
	public CatalogItemsService searchService(CatalogItemRepository catalogItemRepository) {
		return new CatalogItemsService(catalogItemRepository);
	}
}
