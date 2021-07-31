package com.odak.catalogservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.CatalogItemRepository;

public class CatalogItemsService {

	private CatalogItemRepository catalogItemRepository;

	@Autowired
	public CatalogItemsService(CatalogItemRepository catalogItemRepository) {
		this.catalogItemRepository = catalogItemRepository;
	}

	public CatalogItem create(CatalogItem catalogItemDetails) {
		return catalogItemRepository.save(catalogItemDetails);
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItemRepository.getCatalogItems();
	}

	public CatalogItem getCatalogItemById(String itemId) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemId));

		return catalogItem;
	}

	public CatalogItem update(String itemId, CatalogItem catalogItemDetails) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemId));

		return catalogItemRepository.update(catalogItem, catalogItemDetails);
	}

	public void delete(String itemId) throws ResourceNotFoundException {
		catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException("Resource not found: " + itemId));

		catalogItemRepository.delete(itemId);
	}

	public List<CatalogItem> searchByName(String itemName) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(itemName);
		regex.append(")\\s+|\\s+(");
		regex.append(itemName);
		regex.append(")\\s?|^(");
		regex.append(itemName);
		regex.append(")$");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemRepository.catalogItemCollection.stream()
				.filter(item -> pattern.matcher(item.getName().trim()).find()).collect(Collectors.toList());
	}

	public List<CatalogItem> searchByCategories(List<String> criteriaCategories) {

		List<CatalogItem> filteredCollection = new ArrayList<>();

		List<String> lowerCasedCategories = criteriaCategories.stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		for (CatalogItem catalogItem : catalogItemRepository.catalogItemCollection) {
			boolean anyCategoryMatches = catalogItem.getCategories().stream()
					.map(category -> category.getName().toLowerCase())
					.anyMatch(lowerCasedCategories.stream().collect(Collectors.toSet())::contains);
			if (anyCategoryMatches) {
				filteredCollection.add(catalogItem);
			}
		}

		return filteredCollection;
	}

	public List<CatalogItem> searchByText(String text) {
		StringBuilder regex = new StringBuilder(".*(");
		regex.append(text);
		regex.append(").*");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemRepository.catalogItemCollection.stream()
				.filter(catalogItem -> pattern.matcher(catalogItem.getName().trim()).find()
						|| pattern.matcher(catalogItem.getDescription().trim()).find())
				.collect(Collectors.toList());
	}
}
