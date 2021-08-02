package com.odak.catalogservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.helper.query.Query;
import com.odak.catalogservice.helper.query.Query.QueryConfiguration;
import com.odak.catalogservice.helper.search.SearchOperation;
import com.odak.catalogservice.helper.search.SearchOperationFactory;
import com.odak.catalogservice.helper.sort.SortOperation;
import com.odak.catalogservice.helper.sort.SortOperationFactory;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.catalogitem.CatalogItemRepositoryImpl;
import com.odak.catalogservice.repository.category.CategoryRepositoryImpl;
import com.odak.catalogservice.util.string.StringUtil;

public class CatalogItemService {

	private CatalogItemRepositoryImpl catalogItemRepository;
	private CategoryRepositoryImpl categoryRepository;

	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;

	private static final String EXCEPTION_MESSAGE = "Resource with given id not found: ";
	private static final String EXAMPLE_USAGE = "Usage: eq. type=name&value=drill";

	@Autowired
	public CatalogItemService(CatalogItemRepositoryImpl catalogItemRepository,
			CategoryRepositoryImpl categoryRepository) {
		this.catalogItemRepository = catalogItemRepository;
		this.categoryRepository = categoryRepository;
	}

	public CatalogItem create(CatalogItem catalogItem) throws BadRequestException {

		Optional<CatalogItem> catalogItemById = catalogItemRepository.getById(catalogItem.getId());

		if (catalogItemById.isPresent()) {
			throw new BadRequestException("Record with given id already exists: " + catalogItem.getId());
		}
		if (!categoryRepository.getAll().stream().anyMatch(catalogItem.getCategories()::contains)) {
			throw new BadRequestException(
					"Provided category records do not exist. View http://localhost:8080/api/v1/categories for available categories data");
		}

		return catalogItemRepository.save(catalogItem);
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItemRepository.getAll();
	}

	public CatalogItem getCatalogItemById(String itemId) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		return catalogItem;
	}

	public CatalogItem update(String itemId, CatalogItem catalogItemDetails) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		return catalogItemRepository.update(catalogItem, catalogItemDetails);
	}

	public void delete(String itemId) throws ResourceNotFoundException {
		catalogItemRepository.getById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		catalogItemRepository.delete(itemId);
	}

	public Page<CatalogItem> query(HashMap<String, String> queryParams) throws BadRequestException {

		if (queryParams.isEmpty()) {
			return toPage(catalogItemRepository.getAll(), Query.QueryConfiguration.of());
		}

		QueryConfiguration queryConfiguration = Query.QueryConfiguration.of(queryParams.get("type"),
				queryParams.get("value"), tryParseInteger(queryParams.get("limit"), DEFAULT_RECORDS_LIMIT),
				tryParseInteger(queryParams.get("offset"), DEFAULT_PAGE_OFFSET), queryParams.get("sortBy"),
				queryParams.get("sortDir"));

		List<CatalogItem> filteredCollection = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(queryConfiguration.type)) {
			if (StringUtil.isNullOrEmpty(queryConfiguration.value)) {
				throw new BadRequestException("Query type exists, but no value provided. " + EXAMPLE_USAGE);
			}
			SearchOperation targetOperation = SearchOperationFactory.getOperation(queryConfiguration.type)
					.orElseThrow(() -> new BadRequestException(
							"Invalid query type provided: " + queryConfiguration.type + EXAMPLE_USAGE));

			filteredCollection = targetOperation.search(catalogItemRepository.getAll(), queryConfiguration.value);
			return toPage(filteredCollection, queryConfiguration);
		}

		return toPage(catalogItemRepository.getAll(), queryConfiguration);
	}

	private Integer tryParseInteger(String value, Integer defaultValue) {
		try {
			return Integer.parseInt(value);
		} catch (NumberFormatException exception) {
			// Missing logger
			return defaultValue;
		}
	}

	Page<CatalogItem> toPage(List<CatalogItem> catalogItems, QueryConfiguration queryConfiguration)
			throws BadRequestException {

		int totalpages = catalogItems.size() / queryConfiguration.limit;

		PageRequest pageable = PageRequest.of(queryConfiguration.offset, queryConfiguration.limit);

		int max = queryConfiguration.offset >= totalpages ? catalogItems.size()
				: queryConfiguration.limit * (queryConfiguration.offset + 1);
		int min = queryConfiguration.offset > totalpages ? max : queryConfiguration.limit * queryConfiguration.offset;

		List<CatalogItem> sortedCatalogItems = new ArrayList<>();
		if (!StringUtil.isNullOrEmpty(queryConfiguration.sortBy)) {
			SortOperation targetOperation = SortOperationFactory.getOperation(queryConfiguration.sortBy).orElseThrow(
					() -> new BadRequestException("Invalid sort field provided: " + queryConfiguration.sortBy));

			sortedCatalogItems = targetOperation.sort(catalogItems, queryConfiguration.sortDirection);
			return new PageImpl<CatalogItem>(sortedCatalogItems.subList(min, max), pageable, sortedCatalogItems.size());
		}

		return new PageImpl<CatalogItem>(catalogItems.subList(min, max), pageable, catalogItems.size());
	}
}
