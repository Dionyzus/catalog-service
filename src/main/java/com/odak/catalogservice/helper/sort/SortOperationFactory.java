package com.odak.catalogservice.helper.sort;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Factory class invoking sort operation according to provided sort field.
 * 
 * @author ivano
 *
 */
public class SortOperationFactory {

	static Map<String, SortOperation> operationMap = new HashMap<>();
	static {
		operationMap.put(SortOperationType.ID.toString(), new SortById());
		operationMap.put(SortOperationType.NAME.toString(), new SortByName());
		operationMap.put(SortOperationType.PRICE.toString(), new SortByPrice());
	}

	public static Optional<SortOperation> getOperation(String operator) {
		return Optional.ofNullable(operationMap.get(operator));
	}
}
