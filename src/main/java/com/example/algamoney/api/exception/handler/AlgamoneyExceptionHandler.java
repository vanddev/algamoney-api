package com.example.algamoney.api.exception.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.algamoney.api.exception.model.ApiError;
import com.example.algamoney.api.exception.model.ApiValidationError;
import com.example.algamoney.api.exception.service.InactivePersonException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AlgamoneyExceptionHandler{
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<Object> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
		String message = messageSource.getMessage("unrecognized.field", null, null);
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, message, ex));
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
		String message = messageSource.getMessage("validation.error", null, null);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message);
		
		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			apiError.getSubErrors().add(ApiValidationError.builder()
										.object(fieldError.getObjectName())
										.field(fieldError.getField())
										.rejectedValue(fieldError.getRejectedValue())
										.message(fieldError.getDefaultMessage())
										.build());
		}
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<Object> handleEmptyResultDataAccessException() {
		String message = messageSource.getMessage("resource.not-founded", null, null);
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, message));
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
		String message = messageSource.getMessage("resource.invalid-data", null, null);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message, ex);
		return buildResponseEntity(apiError);
	}
	
	@ExceptionHandler(InactivePersonException.class)
	public ResponseEntity<Object> handleInactivePersonException(){
		String message = messageSource.getMessage("inactive.person", null, null);
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, message);
		return buildResponseEntity(apiError);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError apiError){
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}
	
}
