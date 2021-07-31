package com.odak.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;

import com.odak.catalogservice.model.Category;

public class CategoryRepository {

	public final List<Category> categoryCollection = new ArrayList<>();

	public List<Category> getCategories() {
		return categoryCollection;
	}

	public List<Category> findCategoriesByName(String... categories) {

		List<Category> categoryCollection = new ArrayList<>();

		for (String category : categories) {
			categoryCollection.add(getCategory(category));
		}
		return categoryCollection;
	}

	public Category getCategory(String categoryName) {
		return categoryCollection.stream().filter(category -> categoryName.equals(category.getName())).findAny()
				.orElse(null);
	}

	public void save(Category category) {
		categoryCollection.add(category);
	}

	public void saveMany(List<Category> categories) {
		categoryCollection.addAll(categories);
	}

}
