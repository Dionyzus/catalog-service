package com.odak.catalogservice.helper.search;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SearchOperationFactory {

	static Map<String, SearchOperation> operationMap = new HashMap<>();
    static {
        operationMap.put(SearchOperationType.NAME.toString(), new SearchByName());
        operationMap.put(SearchOperationType.TEXT.toString(), new SearchByText());
        operationMap.put(SearchOperationType.CATEGORY.toString(), new SearchByCategory());
    }

    public static Optional<SearchOperation> getOperation(String operator) {
        return Optional.ofNullable(operationMap.get(operator));
    }
}
