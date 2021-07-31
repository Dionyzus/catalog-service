package com.odak.catalogservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.odak.catalogservice.exception.BadRequestException;
import com.odak.catalogservice.exception.ResourceNotFoundException;
import com.odak.catalogservice.helper.search.ISearchOperation;
import com.odak.catalogservice.helper.search.SearchOperationFactory;
import com.odak.catalogservice.helper.sort.ISortOperation;
import com.odak.catalogservice.helper.sort.SortOperationFactory;
import com.odak.catalogservice.model.CatalogItem;
import com.odak.catalogservice.repository.CatalogItemRepository;
import com.odak.catalogservice.repository.CategoryRepository;

public class CatalogItemService {

	private CatalogItemRepository catalogItemRepository;
	private CategoryRepository categoryRepository;

	private static final Integer DEFAULT_RECORDS_LIMIT = 5;
	private static final Integer DEFAULT_PAGE_OFFSET = 0;

	private static final String EXCEPTION_MESSAGE = "Resource with given id not found: ";

	@Autowired
	public CatalogItemService(CatalogItemRepository catalogItemRepository, CategoryRepository categoryRepository) {
		this.catalogItemRepository = catalogItemRepository;
		this.categoryRepository = categoryRepository;
	}

	public CatalogItem create(CatalogItem catalogItem) throws BadRequestException {

		Optional<CatalogItem> catalogItemById = catalogItemRepository.getCatalogItemById(catalogItem.getId());

		if (catalogItemById.isPresent()) {
			throw new BadRequestException("Record with given id already exists: " + catalogItem.getId());
		}
		if (!categoryRepository.getCategories().stream().anyMatch(catalogItem.getCategories()::contains)) {
			throw new BadRequestException(
					"Provided category records do not exist. View http://localhost:8080/api/v1/categories for available categories data");
		}

		return catalogItemRepository.save(catalogItem);
	}

	public List<CatalogItem> getCatalogItems() {
		return catalogItemRepository.getCatalogItems();
	}

	public CatalogItem getCatalogItemById(String itemId) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		return catalogItem;
	}

	public CatalogItem update(String itemId, CatalogItem catalogItemDetails) throws ResourceNotFoundException {
		CatalogItem catalogItem = catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		return catalogItemRepository.update(catalogItem, catalogItemDetails);
	}

	public void delete(String itemId) throws ResourceNotFoundException {
		catalogItemRepository.getCatalogItemById(itemId)
				.orElseThrow(() -> new ResourceNotFoundException(EXCEPTION_MESSAGE + itemId));

		catalogItemRepository.delete(itemId);
	}

	public Page<CatalogItem> query(HashMap<String, String> queryParams) throws BadRequestException {

		if (queryParams.isEmpty()) {
			return toPage(catalogItemRepository.getCatalogItems(), DEFAULT_RECORDS_LIMIT, DEFAULT_PAGE_OFFSET, "", "");
		}

		String searchType = queryParams.get("type") != null ? queryParams.get("type") : "";

		List<String> searchValues = queryParams.get("value") != null
				? Arrays.asList(queryParams.get("value").split(","))
				: new ArrayList<>();

		Integer limit = queryParams.get("limit") != null ? Integer.valueOf(queryParams.get("limit"))
				: DEFAULT_RECORDS_LIMIT;
		Integer offset = queryParams.get("offset") != null ? Integer.valueOf(queryParams.get("offset"))
				: DEFAULT_PAGE_OFFSET;

		String sortField = queryParams.get("sortBy") != null ? queryParams.get("sortBy") : "";
		String sortDirection = queryParams.get("sortDir") != null ? queryParams.get("sortDir") : "";

		List<CatalogItem> filteredCollection = new ArrayList<>();
		if (searchType != "") {
			ISearchOperation targetOperation = SearchOperationFactory.getOperation(searchType)
					.orElseThrow(() -> new BadRequestException("Invalid query type provided: " + searchType));

			filteredCollection = targetOperation.search(catalogItemRepository.getCatalogItems(), searchValues);
			return toPage(filteredCollection, limit, offset, sortField, sortDirection);
		}

		return toPage(catalogItemRepository.getCatalogItems(), limit, offset, sortField, sortDirection);
	}

	Page<CatalogItem> toPage(List<CatalogItem> catalogItems, Integer limit, Integer offset, String sortField,
			String sortDirection) throws BadRequestException {

		int totalpages = catalogItems.size() / limit;

		PageRequest pageable = PageRequest.of(offset, limit);

		int max = offset >= totalpages ? catalogItems.size() : limit * (offset + 1);
		int min = offset > totalpages ? max : limit * offset;

		List<CatalogItem> sortedCatalogItems = new ArrayList<>();
		if (sortField != "") {
			ISortOperation targetOperation = SortOperationFactory.getOperation(sortField)
					.orElseThrow(() -> new BadRequestException("Invalid sort field provided: " + sortField));

			sortedCatalogItems = targetOperation.sort(catalogItems, sortDirection);
			return new PageImpl<CatalogItem>(sortedCatalogItems.subList(min, max), pageable, sortedCatalogItems.size());
		}

		return new PageImpl<CatalogItem>(catalogItems.subList(min, max), pageable, catalogItems.size());
	}
}
