package com.odak.catalogservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class SearchService {

	public List<CatalogItem> searchByName(List<CatalogItem> catalogItemCollection, String itemName) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(itemName);
		regex.append(")\\s+|\\s+(");
		regex.append(itemName);
		regex.append(")\\s?|^(");
		regex.append(itemName);
		regex.append(")$");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream().filter(item -> pattern.matcher(item.getName().trim()).find())
				.collect(Collectors.toList());
	}

	public List<CatalogItem> searchByCategories(List<CatalogItem> catalogItemCollection,
			List<String> criteriaCategories) {

		List<CatalogItem> filteredCollection = new ArrayList<>();

		List<String> lowerCasedCategories = criteriaCategories.stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		for (CatalogItem catalogItem : catalogItemCollection) {
			boolean anyCategoryMatches = catalogItem.getCategories().stream()
					.map(category -> category.getName().toLowerCase())
					.anyMatch(lowerCasedCategories.stream().collect(Collectors.toSet())::contains);
			if (anyCategoryMatches) {
				filteredCollection.add(catalogItem);
			}
		}

		return filteredCollection;
	}

	public List<CatalogItem> searchByText(List<CatalogItem> catalogItemCollection, String text) {
		StringBuilder regex = new StringBuilder(".*(");
		regex.append(text);
		regex.append(").*");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream().filter(catalogItem -> pattern.matcher(catalogItem.getName().trim()).find()
				|| pattern.matcher(catalogItem.getDescription().trim()).find()).collect(Collectors.toList());
	}
}
