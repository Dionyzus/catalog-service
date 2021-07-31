package com.odak.catalogservice.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.odak.catalogservice.model.Image;
import com.odak.catalogservice.repository.ImageRepository;
import com.odak.catalogservice.util.jackson.Deserialization;

public class ImageDataLoader {

	private static final String DATA_SOURCE = "static/images.json";
	
	@Bean
	public CommandLineRunner loadImageData(ImageRepository imageRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		final List<Image> images = Deserialization.deserialize(resource, Image.class);
		
		return (args) -> {
			imageRepository.saveMany(images);
		};
	}
}