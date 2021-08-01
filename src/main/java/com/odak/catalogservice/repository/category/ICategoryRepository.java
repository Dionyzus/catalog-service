package com.odak.catalogservice.repository.category;

import java.util.List;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.IpaRepository;

public interface ICategoryRepository extends IpaRepository<Category> {
	List<Category> getByNames(String... categories);

	Category getByName(String category);
}
