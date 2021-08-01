package com.odak.catalogservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.odak.catalogservice.repository.catalogitem.CatalogItemRepository;
import com.odak.catalogservice.repository.category.CategoryRepository;
import com.odak.catalogservice.repository.image.ImageRepository;
import com.odak.catalogservice.service.CatalogItemService;
import com.odak.catalogservice.service.CategoryService;

@Configuration
@ComponentScan("com.odak.catalogservice.repository")
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
	public CategoryService categoryService(CategoryRepository categoryRepository) {
		return new CategoryService(categoryRepository);
	}

	@Bean
	public CatalogItemService catalogItemService(CatalogItemRepository catalogItemRepository,
			CategoryRepository categoryRepository) {
		return new CatalogItemService(catalogItemRepository, categoryRepository);
	}
}
