package com.odak.catalogservice.helper.search;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.CatalogItem;

/**
 * Performs query by name and description field, returns entries containing provided word either
 * in description or item name.
 * 
 * @author ivano
 *
 */
public class SearchByText implements SearchOperation {
	
	@Override
	public List<CatalogItem> search(List<CatalogItem> catalogItemCollection, String text) {
		StringBuilder regex = new StringBuilder(".*(");
		regex.append(text);
		regex.append(").*");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemCollection.stream()
				.filter(catalogItem -> pattern.matcher(catalogItem.getName().trim()).find()
						|| pattern.matcher(catalogItem.getDescription().trim()).find())
				.collect(Collectors.toList());
	}
}
