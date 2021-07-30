package com.odak.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemRepository {

	public final List<CatalogItem> catalogItemCollection = new ArrayList<>();
	
	public List<CatalogItem> getAllCatalogItems() {
		return catalogItemCollection;
	}
	
	public CatalogItem save(CatalogItem catalogItem) {
		catalogItemCollection.add(catalogItem);
		return catalogItem;
	}
	
	public void save(List<CatalogItem> catalogItems) {
		catalogItemCollection.addAll(catalogItems);
	}

	public Optional<CatalogItem> getCatalogItemByName(String itemName) {
		return catalogItemCollection.stream()
				.filter(catalogItem -> itemName.equalsIgnoreCase(catalogItem.getName()))
				.findAny();
	}

	public void deleteByName(String itemName) {
		catalogItemCollection.removeIf(catalogItem -> catalogItem.getName().equalsIgnoreCase(itemName));
	}

	public void update(CatalogItem catalogItem, CatalogItem catalogItemDetails) {
		catalogItem.setName(catalogItemDetails.getName());
		catalogItem.setDescription(catalogItemDetails.getDescription());
		catalogItem.setPrice(catalogItemDetails.getPrice());
		catalogItem.setImages(catalogItemDetails.getImages());
		catalogItem.setCategories(catalogItemDetails.getCategories());
	}
}
