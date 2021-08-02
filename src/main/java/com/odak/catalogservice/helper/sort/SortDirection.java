package com.odak.catalogservice.helper.sort;

/**
 * Available sort directions.
 * @author ivano
 *
 */
public enum SortDirection {
	ASC("asc"), DESC("desc");

	private final String value;

	SortDirection(final String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return value;
	}
}
