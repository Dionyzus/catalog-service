package com.odak.catalogservice.repository.category;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.odak.catalogservice.model.Category;

public class CategoryRepository implements ICategoryRepository {

	public final List<Category> categoryCollection = new ArrayList<>();

	@Override
	public List<Category> getAll() {
		return categoryCollection;
	}

	@Override
	public List<Category> getByNames(String... categories) {

		List<Category> categoryCollection = new ArrayList<>();

		for (String categoryName : categories) {
			Category category = getByName(categoryName);
			if (category != null) {
				categoryCollection.add(category);
			}
		}
		return categoryCollection;
	}

	@Override
	public Category getByName(String categoryName) {
		return categoryCollection.stream().filter(category -> categoryName.equals(category.getName())).findAny()
				.orElse(null);
	}

	@Override
	public Optional<Category> getById(String id) {
		return categoryCollection.stream().filter(category -> id.equals(category.getId())).findAny();
	}

	@Override
	public Category save(Category category) {
		categoryCollection.add(category);
		return category;
	}

	@Override
	public void saveMany(List<Category> categories) {
		categoryCollection.addAll(categories);
	}

	@Override
	public void delete(String id) {
		categoryCollection.removeIf(category -> category.getId().equals(id));
	}

	@Override
	public Category update(Category category, Category categoryDetails) {
		category.setName(categoryDetails.getName());
		return category;
	}
}
