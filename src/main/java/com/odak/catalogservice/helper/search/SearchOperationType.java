package com.odak.catalogservice.helper.search;

/**
 * Available search operation types.
 * @author ivano
 *
 */
public enum SearchOperationType {
	NAME("name"), TEXT("text"), CATEGORY("category");

	private final String value;

	SearchOperationType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
