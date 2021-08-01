package com.odak.catalogservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.category.CategoryRepositoryImpl;

public class CategoryService {

	private CategoryRepositoryImpl categoryRepository;

	@Autowired
	public CategoryService(CategoryRepositoryImpl categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getCategories() {
		return categoryRepository.getAll();
	}
}
