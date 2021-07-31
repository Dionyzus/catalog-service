package com.odak.catalogservice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

public class CatalogItemsService {

	private CatalogItemRepository catalogItemRepository;

	private static final Integer DEFAULT_PAGE_SIZE = 5;
	private static final Integer DEFAULT_PAGE_NUMBER = 0;

	private static final String EXCEPTION_MESSAGE = "Resource not found: ";

	@Autowired
	public CatalogItemsService(CatalogItemRepository catalogItemRepository) {
		this.catalogItemRepository = catalogItemRepository;
	}

	public CatalogItem create(CatalogItem catalogItemDetails) {
		return catalogItemRepository.save(catalogItemDetails);
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

		String searchType = queryParams.get("type") != null ? queryParams.get("type") : "";
		List<String> searchValues = queryParams.get("value") != null
				? Arrays.asList(queryParams.get("value").split(","))
				: new ArrayList<>();

		Integer pageSize = queryParams.get("pageSize") != null ? Integer.valueOf(queryParams.get("pageSize"))
				: DEFAULT_PAGE_SIZE;
		Integer pageNumber = queryParams.get("pageNo") != null ? Integer.valueOf(queryParams.get("pageNo"))
				: DEFAULT_PAGE_NUMBER;
		String sortField = queryParams.get("sortBy") != null ? queryParams.get("sortBy") : "";
		String sortDirection = queryParams.get("sortDir") != null ? queryParams.get("sortDir") : "";

		ISearchOperation targetOperation = SearchOperationFactory.getOperation(searchType)
				.orElseThrow(() -> new BadRequestException("Invalid query param provided: " + searchType));

		List<CatalogItem> filteredCollection = targetOperation.search(catalogItemRepository.getCatalogItems(),
				searchValues);

		return toPage(filteredCollection, pageSize, pageNumber, sortField, sortDirection);
	}

	public List<CatalogItem> searchByName(String itemName) {
		StringBuilder regex = new StringBuilder("^(");
		regex.append(itemName);
		regex.append(")\\s+|\\s+(");
		regex.append(itemName);
		regex.append(")\\s?|^(");
		regex.append(itemName);
		regex.append(")$");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemRepository.catalogItemCollection.stream()
				.filter(item -> pattern.matcher(item.getName().trim()).find()).collect(Collectors.toList());
	}

	public List<CatalogItem> searchByCategories(List<String> criteriaCategories) {

		List<CatalogItem> filteredCollection = new ArrayList<>();

		List<String> lowerCasedCategories = criteriaCategories.stream().map(String::toLowerCase)
				.collect(Collectors.toList());

		for (CatalogItem catalogItem : catalogItemRepository.catalogItemCollection) {
			boolean anyCategoryMatches = catalogItem.getCategories().stream()
					.map(category -> category.getName().toLowerCase())
					.anyMatch(lowerCasedCategories.stream().collect(Collectors.toSet())::contains);
			if (anyCategoryMatches) {
				filteredCollection.add(catalogItem);
			}
		}

		return filteredCollection;
	}

	public List<CatalogItem> searchByText(String text) {
		StringBuilder regex = new StringBuilder(".*(");
		regex.append(text);
		regex.append(").*");

		Pattern pattern = Pattern.compile(regex.toString(), Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);

		return catalogItemRepository.catalogItemCollection.stream()
				.filter(catalogItem -> pattern.matcher(catalogItem.getName().trim()).find()
						|| pattern.matcher(catalogItem.getDescription().trim()).find())
				.collect(Collectors.toList());
	}

	Page<CatalogItem> toPage(List<CatalogItem> catalogItems, Integer pageSize, Integer pageNumber, String sortField,
			String sortDirection) throws BadRequestException {

		int totalpages = catalogItems.size() / pageSize;

		ISortOperation targetOperation = SortOperationFactory.getOperation(sortField)
				.orElseThrow(() -> new BadRequestException("Invalid sort field provided: " + sortField));

		targetOperation.sort(catalogItems, sortDirection);

		PageRequest pageable = PageRequest.of(pageNumber, pageSize);

		int max = pageNumber >= totalpages ? catalogItems.size() : pageSize * (pageNumber + 1);
		int min = pageNumber > totalpages ? max : pageSize * pageNumber;

		return new PageImpl<CatalogItem>(catalogItems.subList(min, max), pageable, catalogItems.size());
	}
}
