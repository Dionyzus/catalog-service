package com.odak.catalogservice.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.category.CategoryRepositoryImpl;
import com.odak.catalogservice.util.jackson.Deserialization;

@Component
public class CategoryDataLoader {

	private static final String DATA_SOURCE = "static/categories.json";

	/**
	 * Loads initial category data.
	 *
	 * @param categoryRepository - repository handling data.
	 * @return {@link CommandLineRunner}.
	 */
	@Bean
	public CommandLineRunner loadCategoryData(CategoryRepositoryImpl categoryRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		final List<Category> categories = Deserialization.deserialize(resource, Category.class);

		return (args) -> {
			categoryRepository.saveMany(categories);
		};
	}
}
