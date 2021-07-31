package com.odak.catalogservice.helpers.search;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

public class SearchByName implements ISearchOperation{

	@Override
	public List<CatalogItem> search(List<CatalogItem> catalogItemCollection, List<String> options) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(options.get(0));
		regex.append(")\\s+|\\s+(");
		regex.append(options.get(0));
		regex.append(")\\s?|^(");
		regex.append(options.get(0));
		regex.append(")$");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream()
				.filter(item -> pattern.matcher(item.getName().trim()).find()).collect(Collectors.toList());
	}
}
