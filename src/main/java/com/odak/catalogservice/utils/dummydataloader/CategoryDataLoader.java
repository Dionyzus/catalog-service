package com.odak.catalogservice.utils.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.CategoryRepository;
import com.odak.catalogservice.utils.jackson.Deserialization;

@Component
public class CategoryDataLoader {

	@Bean
	public CommandLineRunner loadCategoryData(CategoryRepository categoryRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource("static/categories.json");

		final List<Category> categories = Deserialization.deserialize(resource, Category.class);

		return (args) -> {
			categoryRepository.saveMany(categories);
		};
	}
}
