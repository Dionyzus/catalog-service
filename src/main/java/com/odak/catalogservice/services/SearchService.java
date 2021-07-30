package com.odak.catalogservice.services;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class SearchService {

	public List<CatalogItem> searchByName(List<CatalogItem> catalogItemCollection, String criteria) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(criteria);
		regex.append(")\\s+|\\s+(");
		regex.append(criteria);
		regex.append(")\\s?|^(");
		regex.append(criteria);
		regex.append(")$");
		
		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream()
				.filter(item -> pattern.matcher(item.getName().trim()).find())
				.collect(Collectors.toList());
	}
}
