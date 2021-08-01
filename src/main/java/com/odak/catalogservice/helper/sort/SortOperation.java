package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

public interface SortOperation {
	List<CatalogItem> sort(List<CatalogItem> catalogItemCollection, String sortDirection);
}
