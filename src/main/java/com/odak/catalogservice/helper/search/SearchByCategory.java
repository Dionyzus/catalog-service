package com.odak.catalogservice.helper.search;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class SearchByCategory implements SearchOperation {

	@Override
	public List<CatalogItem> search(List<CatalogItem> catalogItemCollection, List<String> criteria) {
		List<CatalogItem> filteredCollection = new ArrayList<>();

		List<String> lowerCasedCategories = criteria.stream().map(String::toLowerCase).collect(Collectors.toList());

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
}
