package com.odak.catalogservice.repository.image;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.odak.catalogservice.model.Image;

public class ImageRepositoryImpl implements ImageRepository {

	public final List<Image> imageCollection = new ArrayList<>();

	@Override
	public List<String> getUriLinksByCatalogItemName(String catalogItemName) {
		return imageCollection.stream().map(Image::getUriLink)
				.filter(itemName -> catalogItemName.equalsIgnoreCase(itemName)).collect(Collectors.toList());
	}

	@Override
	public Image save(Image image) {
		imageCollection.add(image);
		return image;
	}

	@Override
	public void saveMany(List<Image> images) {
		imageCollection.addAll(images);
	}

	@Override
	public List<Image> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Image> getById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Image update(Image entity, Image newEntityData) {
		// TODO Auto-generated method stub
		return null;
	}
}
