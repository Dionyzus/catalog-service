package com.odak.catalogservice.repository.image;

import java.util.List;

import com.odak.catalogservice.model.Image;
import com.odak.catalogservice.repository.IpaRepository;

public interface IImageRepository extends IpaRepository<Image> {
	public List<String> getUriLinksByCatalogItemName(String catalogItemName);
}
