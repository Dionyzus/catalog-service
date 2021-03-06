package com.odak.catalogservice.util.dummydataloader;

import java.net.URL;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.catalogitem.CatalogItemRepositoryImpl;
import com.odak.catalogservice.util.jackson.Deserialization;

@Component
public class CatalogItemDataLoader {

	private static final String DATA_SOURCE = "static/catalog_items.json";
	
	/**
	 * Loads initial catalog item data.
	 *
	 * @param catalogItemRepository - repository handling data.
	 * @return {@link CommandLineRunner}.
	 */
	@Bean
	public CommandLineRunner loadCatalogItemData(CatalogItemRepositoryImpl catalogItemRepository) {

		URL resource = Thread.currentThread().getContextClassLoader().getResource(DATA_SOURCE);

		final List<CatalogItem> catalogItems = Deserialization.deserialize(resource, CatalogItem.class);
		
		return (args) -> {
			catalogItemRepository.saveMany(catalogItems);
		};
	}
}
