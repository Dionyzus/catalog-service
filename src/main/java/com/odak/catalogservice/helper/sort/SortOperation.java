package com.odak.catalogservice.helper.sort;

import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

/**
 * Interface defining abstraction to perform sort operation.
 *
 * @author ivano
 *
 */
public interface SortOperation {
	List<CatalogItem> sort(List<CatalogItem> catalogItemCollection, String sortDirection);
}
