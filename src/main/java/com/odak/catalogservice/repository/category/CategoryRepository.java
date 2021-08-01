package com.odak.catalogservice.repository.category;

import java.util.List;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.CustomPersistanceApiRepository;

public interface CategoryRepository extends CustomPersistanceApiRepository<Category> {
	List<Category> getByNames(String... categories);

	Category getByName(String category);
}
