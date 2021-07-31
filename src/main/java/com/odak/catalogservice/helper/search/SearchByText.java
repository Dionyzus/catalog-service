package com.odak.catalogservice.helper.search;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class SearchByText implements ISearchOperation {
	
	@Override
	public List<CatalogItem> search(List<CatalogItem> catalogItemCollection, List<String> options) {
		StringBuilder regex = new StringBuilder(".*(");
		regex.append(options.get(0));
		regex.append(").*");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream()
				.filter(catalogItem -> pattern.matcher(catalogItem.getName().trim()).find()
						|| pattern.matcher(catalogItem.getDescription().trim()).find())
				.collect(Collectors.toList());
	}
}
