package com.odak.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemRepository {

	public final List<CatalogItem> catalogItemCollection = new ArrayList<>();
	
	public List<CatalogItem> getCatalogItems() {
		return catalogItemCollection;
	}
	
	public CatalogItem save(CatalogItem catalogItem) {
		catalogItemCollection.add(catalogItem);
		return catalogItem;
	}
	
	public void saveMany(List<CatalogItem> catalogItems) {
		catalogItemCollection.addAll(catalogItems);
	}

	public Optional<CatalogItem> getCatalogItemById(String itemId) {
		return catalogItemCollection.stream()
				.filter(catalogItem -> itemId.equals(catalogItem.getId()))
				.findAny();
	}

	public void delete(String itemId) {
		catalogItemCollection.removeIf(catalogItem -> catalogItem.getId().equals(itemId));
	}

	public CatalogItem update(CatalogItem catalogItem, CatalogItem catalogItemDetails) {
		catalogItem.setName(catalogItemDetails.getName());
		catalogItem.setDescription(catalogItemDetails.getDescription());
		catalogItem.setPrice(catalogItemDetails.getPrice());
		catalogItem.setImages(catalogItemDetails.getImages());
		catalogItem.setCategories(catalogItemDetails.getCategories());
		
		return catalogItem;
	}
}
