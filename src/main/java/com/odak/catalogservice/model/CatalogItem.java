package com.odak.catalogservice.model;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Catalog item data representation object.
 * @author ivano
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatalogItem {
	@JsonProperty("id")
	@NotNull(message = "The id is required.")
	private String id;

	@JsonProperty("name")
	@NotNull(message = "The name is required.")
	@Size(max = 100, message = "The length of an item name must be between 5 and 100 characters.")
	private String name;

	@JsonProperty("description")
	@NotNull(message = "The description is required.")
	@Size(max = 2000, message = "The description must be between 10 and 2000 characters.")
	private String description;

	@NotNull(message = "The item price is required.")
	@JsonProperty("price")
	private Double price;

	@JsonProperty("images")
	private List<String> images;

	@NotEmpty(message = "Item category is required.")
	@JsonProperty("categories")
	@Size(min = 1, message = "Item should belong to at least one category.")
	private List<Category> categories;
}
