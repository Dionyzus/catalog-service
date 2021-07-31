package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.helper.sort.CatalogItemSorter.PriceComparator;
import com.odak.catalogservice.model.CatalogItem;

public class SortByPrice implements ISortOperation {

	@Override
	public void sort(List<CatalogItem> catalogItemCollection, String sortDirection) {
		CatalogItemSorter.sort(catalogItemCollection, sortDirection, new PriceComparator());
	}
}
