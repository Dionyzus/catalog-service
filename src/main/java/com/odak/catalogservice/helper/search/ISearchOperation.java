package com.odak.catalogservice.helper.search;

import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

public interface ISearchOperation {
	List<CatalogItem> search(List<CatalogItem> catalogItemCollection, List<String> options);
}
