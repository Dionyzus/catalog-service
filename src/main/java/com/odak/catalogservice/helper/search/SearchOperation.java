package com.odak.catalogservice.helper.search;

import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

/**
 * Interface defining abstraction to perform search operation.
 *
 * @author ivano
 *
 */
public interface SearchOperation {
	List<CatalogItem> search(List<CatalogItem> catalogItemCollection, String criteria);
}
