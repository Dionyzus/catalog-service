package com.odak.catalogservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data class defining API error description.
 * @author ivano
 *
 */
@Data
public class ApiError {

	private HttpStatus status;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private List<ApiValidationError> errors;

	private ApiError() {
		timestamp = LocalDateTime.now();
	}

	/**
	 * Instantiates new {@link ApiError} with provided HTTP status.
	 * @param status - {@link HttpStatus}.
	 */
	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	/**
	 * Instantiates new {@link ApiError} with provided HTTP status and exception.
	 * 
	 * @param status - {@link HttpStatus}.
	 * @param ex - {@link Throwable} instance.
	 */
	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
	}

	/**
	 * Instantiates new {@link ApiError} with provided HTTP status and exception.
	 *
	 * @param status - {@link HttpStatus}.
	 * @param message - additional message value.
	 * @param ex - {@link Throwable} instance.
	 */
	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
	}

	private void addSubError(ApiValidationError error) {
		if (errors == null) {
			errors = new ArrayList<>();
		}
		errors.add(error);
	}
	
	/**
	 * Adds to global errors list.
	 *
	 * @param globalErrors - list containing {@link ObjectError}.
	 */
	public void addValidationError(List<ObjectError> globalErrors) {
		globalErrors.forEach(this::addValidationError);
	}
	
	/**
	 * Adds to field errors list.
	 *
	 * @param fieldErrors - list containing {@link FieldError}.
	 */
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(this::addValidationError);
	}

	private void addValidationError(String object, String field, Object rejectedValue, String message) {
		addSubError(new ApiValidationError(object, field, rejectedValue, message));
	}

	private void addValidationError(String object, String message) {
		addSubError(new ApiValidationError(object, message));
	}

	private void addValidationError(FieldError fieldError) {
		this.addValidationError(fieldError.getObjectName(), fieldError.getField(), fieldError.getRejectedValue(),
				fieldError.getDefaultMessage());
	}

	private void addValidationError(ObjectError objectError) {
		this.addValidationError(objectError.getObjectName(), objectError.getDefaultMessage());
	}
}
