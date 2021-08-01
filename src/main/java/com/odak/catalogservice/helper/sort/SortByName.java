package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.helper.sort.CatalogItemSorter.NameComparator;
import com.odak.catalogservice.model.CatalogItem;

public class SortByName implements SortOperation {

	@Override
	public List<CatalogItem> sort(List<CatalogItem> catalogItemCollection, String sortDirection) {
		return CatalogItemSorter.sort(catalogItemCollection, sortDirection, new NameComparator());
	}
}
