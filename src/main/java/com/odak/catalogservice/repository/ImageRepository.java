package com.odak.catalogservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.Image;

public class ImageRepository {

	public final List<Image> imageCollection = new ArrayList<>();
	
	public List<String> getUriLinks(String catalogItemName) {
		return imageCollection.stream()
				.map(Image::getUriLink)
				.filter(itemName -> catalogItemName.equalsIgnoreCase(itemName))
				.collect(Collectors.toList());
	}
	
	public void save(Image image) {
		imageCollection.add(image);
	}
	
	public void save(List<Image> images) {
		imageCollection.addAll(images);
	}
}
