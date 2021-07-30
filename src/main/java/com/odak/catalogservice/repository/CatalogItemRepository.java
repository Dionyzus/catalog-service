package com.odak.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemRepository {

	public final List<CatalogItem> catalogItemCollection = new ArrayList<>();
	
	public List<CatalogItem> getAllCatalogItems() {
		return catalogItemCollection;
	}
	
	public void save(CatalogItem catalogItem) {
		catalogItemCollection.add(catalogItem);
	}
	
	public void save(List<CatalogItem> catalogItems) {
		catalogItemCollection.addAll(catalogItems);
	}

}
