package com.odak.catalogservice.util.string;

public class StringUtil {

	private StringUtil() {
		throw new UnsupportedOperationException("Utils class instantiation not allowed.");
	}
	
	public static boolean isNullOrEmpty(String value) {
		if (value == "" || value == null) {
			return true;
		}
		return false;
	}
}
