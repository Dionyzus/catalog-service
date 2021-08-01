package com.odak.catalogservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.repository.category.CategoryRepository;

public class CategoryService {

	private CategoryRepository categoryRepository;

	@Autowired
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<Category> getCategories() {
		return categoryRepository.getAll();
	}
}
