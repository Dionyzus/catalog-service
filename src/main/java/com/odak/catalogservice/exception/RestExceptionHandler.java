package com.odak.catalogservice.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.odak.catalogservice.model.ApiError;

/**
 * Custom exception handler which handles request exceptions and validation
 * errors.
 *
 * @author ivano
 *
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage("Validation error");
		apiError.addValidationErrors(exception.getBindingResult().getFieldErrors());
		apiError.addValidationError(exception.getBindingResult().getGlobalErrors());

		return buildResponseEntity(apiError);
	}

	/**
	 * Handles resource not found exception.
	 *
	 * @param exception - {@link ResourceNotFoundException} instance.
	 * @return - {@link ResponseEntity} containing additional error data.
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	protected ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException exception) {
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND);
		apiError.setMessage(exception.getMessage());
		return buildResponseEntity(apiError);
	}

	/**
	 * Handles resource not found exception.
	 *
	 * @param exception - {@link BadRequestException} instance.
	 * @return - {@link ResponseEntity} containing additional error data.
	 */
	@ExceptionHandler(BadRequestException.class)
	protected ResponseEntity<Object> handleMissingCategoryBadRequest(BadRequestException exception) {
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST);
		apiError.setMessage(exception.getMessage());
		return buildResponseEntity(apiError);
	}

	private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
}