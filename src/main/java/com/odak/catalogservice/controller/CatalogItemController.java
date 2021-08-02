package com.odak.catalogservice.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.service.CatalogItemService;

@RestController
@RequestMapping("api/v1")
public class CatalogItemController {

	private CatalogItemService catalogItemService;

	@Autowired
	public CatalogItemController(CatalogItemService catalogItemsService) {
		this.catalogItemService = catalogItemsService;
	}

	/**
	 * Creates new catalog item entry.
	 *
	 * @param catalogItemDetails - {@link CatalogItem} instance with required data
	 *                           in JSON format.
	 * @return {@link ResponseEntity} - JSON response containing newly created data.
	 */
	@RequestMapping(value = "/catalog-items", method = RequestMethod.POST, produces = {
			"application/json" }, consumes = "application/json")
	public ResponseEntity<CatalogItem> createCatalogItem(@Validated @RequestBody CatalogItem catalogItemDetails) {

		CatalogItem catalogItem = catalogItemService.create(catalogItemDetails);

		return ResponseEntity.status(HttpStatus.CREATED).body(catalogItem);
	}

	/**
	 * Gets list of {@link CatalogItem}. Available search types: name, text,
	 * category; available sort fields: id, name, price.
	 * 
	 * Example usage:
	 * http://localhost:8080/api/v1/catalog-items?limit=6&type=category&value=Desktop
	 * computers,Gaming laptops&sortBy=price&sortDir=asc
	 * http://localhost:8080/api/v1/catalog-items?type=text&value=drill&sortBy=name&sortDir=asc
	 * http://localhost:8080/api/v1/catalog-items
	 * 
	 * @param queryParams - query parameters, available parameters: limit, offset,
	 *                    sortBy, sortDir, type (search type), value (search value).
	 *
	 * @return {@link ResponseEntity} of {@link Page} type containing list of
	 *         {@link CatalogItem}.
	 * @throws BadRequestException - if provided parameters do not exist or
	 *                             search/sort method does not exist.
	 */
	@RequestMapping(value = "/catalog-items", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<Page<CatalogItem>> getCatalogItems(
			@RequestParam(required = false) HashMap<String, String> queryParams) throws BadRequestException {

		Page<CatalogItem> catalogItems = catalogItemService.query(queryParams);

		return ResponseEntity.ok(catalogItems);
	}

	/**
	 * Gets catalog item if entry exists in collection.
	 *
	 * @param itemId - requested catalog item id.
	 * @return {@link ResponseEntity} JSON response containing matched id data.
	 * @throws ResourceNotFoundException - if item with provided id does not exist,
	 *                                   {@link HttpStatus.NOT_FOUND} otherwise.
	 */
	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.GET, produces = { "application/json" })
	public ResponseEntity<CatalogItem> getCatalogItemById(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemService.getCatalogItemById(itemId);

		return ResponseEntity.ok(catalogItem);
	}

	/**
	 * Updates catalog item if entry exists in collection.
	 *
	 * @param itemId - requested catalog item id
	 * @param catalogItemDetails - JSON request body containing updated fields.
	 * @return {@link ResponseEntity} - JSON containing updated catalog item,
	 *         {@link HttpStatus.NOT_FOUND} otherwise.
	 * @throws ResourceNotFoundException - if item with provided id does not exist.
	 */
	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.PUT, produces = {
			"application/json" }, consumes = "application/json")
	public ResponseEntity<CatalogItem> updateCatalogItem(@Validated @PathVariable(value = "id") String itemId,
			@RequestBody CatalogItem catalogItemDetails) throws ResourceNotFoundException {

		CatalogItem catalogItem = catalogItemService.update(itemId, catalogItemDetails);

		return ResponseEntity.ok(catalogItem);
	}

	/**
	 * Deletes catalog item if entry exists in collection.
	 *
	 * @param itemId - requested catalog item id
	 * @return response {@link HttpStatus.NO_CONTENT} if action was successful, bad
	 *         request otherwise.
	 * @throws ResourceNotFoundException - if item with provided id does not exist.
	 */
	@RequestMapping(value = "/catalog-items/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<CatalogItem> deleteCatalogItem(@PathVariable(value = "id") String itemId)
			throws ResourceNotFoundException {

		catalogItemService.delete(itemId);

		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
