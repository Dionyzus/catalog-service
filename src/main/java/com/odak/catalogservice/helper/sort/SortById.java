package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.helper.sort.CatalogItemSorter.IdComparator;
import com.odak.catalogservice.model.CatalogItem;

/**
 * Sorts collection by item id.
 * @author ivano
 *
 */
public class SortById implements SortOperation {

	@Override
	public List<CatalogItem> sort(List<CatalogItem> catalogItemCollection, String sortDirection) {
		return CatalogItemSorter.sort(catalogItemCollection, sortDirection, new IdComparator());
	}
}
