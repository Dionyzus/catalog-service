package com.odak.catalogservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.model.Category;
import com.odak.catalogservice.service.CategoryService;

@RestController
@RequestMapping("api/v1")
public class CategoryController {

	private CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService catalogItemsService) {
		this.categoryService = catalogItemsService;
	}

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<List<Category>> getCategories() throws BadRequestException {

		List<Category> categories = categoryService.getCategories();

		return ResponseEntity.ok(categories);
	}
}
