package com.odak.catalogservice.helper.search;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

/**
 * Performs query by name field, returns only values matching provided word.
 *
 * @author ivano
 *
 */
public class SearchByName implements SearchOperation{

	@Override
	public List<CatalogItem> search(List<CatalogItem> catalogItemCollection, String name) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(name);
		regex.append(")\\s+|\\s+(");
		regex.append(name);
		regex.append(")\\s?|^(");
		regex.append(name);
		regex.append(")$");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream()
				.filter(item -> pattern.matcher(item.getName().trim()).find()).collect(Collectors.toList());
	}
}
