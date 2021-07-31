package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

public interface ISortOperation {
	void sort(List<CatalogItem> catalogItemCollection, String sortDirection);
}
