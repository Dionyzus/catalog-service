package com.odak.catalogservice.helpers.sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class SortOperationFactory {

	static Map<String, ISortOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(SortOperationType.ID.toString(), new SortById());
		operationMap.put(SortOperationType.NAME.toString(), new SortByName());
		operationMap.put(SortOperationType.PRICE.toString(), new SortByPrice());
	}

	public static Optional<ISortOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
