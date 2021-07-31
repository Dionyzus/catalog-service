package com.odak.catalogservice.helper.sort;

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
