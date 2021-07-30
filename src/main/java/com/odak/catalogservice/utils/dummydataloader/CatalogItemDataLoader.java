package com.odak.catalogservice.utils.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.CatalogItemRepository;
import com.odak.catalogservice.utils.jackson.Deserialization;

@Component
public class CatalogItemDataLoader {

	@Bean
	public CommandLineRunner loadCatalogItemData(CatalogItemRepository catalogItemRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource("static/catalog_items.json");

		final List<CatalogItem> catalogItems = Deserialization.deserialize(resource, CatalogItem.class);
		
		return (args) -> {
			catalogItemRepository.save(catalogItems);
		};
	}
}
