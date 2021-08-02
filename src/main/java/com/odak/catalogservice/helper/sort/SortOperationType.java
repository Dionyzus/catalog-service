package com.odak.catalogservice.helper.sort;

/**
 * Available sort field options.
 * @author ivano
 *
 */
public enum SortOperationType {
	ID("id"), NAME("name"), PRICE("price");

	private final String value;

	SortOperationType(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
