package com.odak.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.odak.catalogservice.repository.catalogitem.CatalogItemRepositoryImpl;
import com.odak.catalogservice.repository.category.CategoryRepositoryImpl;
import com.odak.catalogservice.repository.image.ImageRepositoryImpl;
import com.odak.catalogservice.service.CatalogItemService;
import com.odak.catalogservice.service.CategoryService;

@Configuration
@ComponentScan("com.odak.catalogservice.repository")
public class Config {

	@Bean
	public CategoryRepositoryImpl categoryRepository() {
		return new CategoryRepositoryImpl();
	}

	@Bean
	public ImageRepositoryImpl imageRepository() {
		return new ImageRepositoryImpl();
	}

	@Bean
	public CatalogItemRepositoryImpl catalogItemRepository() {
		return new CatalogItemRepositoryImpl();
	}

	@Bean
	public CategoryService categoryService(CategoryRepositoryImpl categoryRepository) {
		return new CategoryService(categoryRepository);
	}

	@Bean
	public CatalogItemService catalogItemService(CatalogItemRepositoryImpl catalogItemRepository,
			CategoryRepositoryImpl categoryRepository) {
		return new CatalogItemService(catalogItemRepository, categoryRepository);
	}
}
