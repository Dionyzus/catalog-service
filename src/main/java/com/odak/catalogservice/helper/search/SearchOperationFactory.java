package com.odak.catalogservice.helper.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Factory class invoking search operation according to provided search type.
 * 
 * @author ivano
 *
 */
public class SearchOperationFactory {

	static Map<String, SearchOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(SearchOperationType.NAME.toString(), new SearchByName());
		operationMap.put(SearchOperationType.TEXT.toString(), new SearchByText());
		operationMap.put(SearchOperationType.CATEGORY.toString(), new SearchByCategory());
	}

	/**
	 * Gets search operation type.
	 *
	 * @param operator - String representing search operation type.
	 * @return operation instance if it exists.
	 */
	public static Optional<SearchOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
