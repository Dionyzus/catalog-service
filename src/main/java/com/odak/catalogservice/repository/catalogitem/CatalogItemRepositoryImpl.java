package com.odak.catalogservice.repository.catalogitem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.odak.catalogservice.model.CatalogItem;

public class CatalogItemRepositoryImpl implements CatalogItemRepository {

	public final List<CatalogItem> catalogItemCollection = new ArrayList<>();

	@Override
	public List<CatalogItem> getAll() {
		return catalogItemCollection;
	}

	@Override
	public CatalogItem save(CatalogItem catalogItem) {
		catalogItemCollection.add(catalogItem);
		return catalogItem;
	}

	@Override
	public void saveMany(List<CatalogItem> catalogItems) {
		catalogItemCollection.addAll(catalogItems);
	}

	@Override
	public Optional<CatalogItem> getById(String itemId) {
		return catalogItemCollection.stream().filter(catalogItem -> itemId.equals(catalogItem.getId())).findAny();
	}

	@Override
	public void delete(String id) {
		catalogItemCollection.removeIf(catalogItem -> catalogItem.getId().equals(id));
	}

	@Override
	public CatalogItem update(CatalogItem catalogItem, CatalogItem catalogItemDetails) {
		catalogItem.setName(catalogItemDetails.getName());
		catalogItem.setDescription(catalogItemDetails.getDescription());
		catalogItem.setPrice(catalogItemDetails.getPrice());
		catalogItem.setImages(catalogItemDetails.getImages());
		catalogItem.setCategories(catalogItemDetails.getCategories());

		return catalogItem;
	}
}
