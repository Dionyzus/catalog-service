package com.odak.catalogservice.exception;

/**
 * Base class for bad request exception.
 *
 * @author ivano
 *
 */
public class BadRequestException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadRequestException(String message) {
		super(message);
	}
}
