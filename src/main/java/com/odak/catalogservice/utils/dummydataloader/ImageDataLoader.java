package com.odak.catalogservice.utils.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import com.odak.catalogservice.model.Image;
import com.odak.catalogservice.repository.ImageRepository;
import com.odak.catalogservice.utils.jackson.Deserialization;

public class ImageDataLoader {

	@Bean
	public CommandLineRunner loadImageData(ImageRepository imageRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource("static/images.json");

		final List<Image> images = Deserialization.deserialize(resource, Image.class);
		
		return (args) -> {
			imageRepository.save(images);
		};
	}
}
