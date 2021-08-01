package com.odak.catalogservice.repository.image;

import java.util.List;

import com.odak.catalogservice.model.Image;
import com.odak.catalogservice.repository.CustomPersistanceApiRepository;

public interface ImageRepository extends CustomPersistanceApiRepository<Image> {
	public List<String> getUriLinksByCatalogItemName(String catalogItemName);
}
