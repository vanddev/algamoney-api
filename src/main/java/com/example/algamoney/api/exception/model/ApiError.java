package com.example.algamoney.api.exception.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ApiError {

	private HttpStatus status;
	@JsonFormat(pattern = "MM/dd/yyyy HH:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	@JsonInclude(Include.NON_NULL)
	private String messageDetail;
	@JsonProperty("sub_errors")
	@JsonInclude(Include.NON_EMPTY)
	private List<ApiSubError> subErrors = new ArrayList<>();
	
	private ApiError() {
		timestamp = LocalDateTime.now();
	}
	
	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}
	
	public ApiError(HttpStatus status, String message) {
		this();
		this.status = status;
		this.message = message;
	}
	
	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.messageDetail = ex.getMessage();
	}
	
	public ApiError(HttpStatus status, String message,  Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.messageDetail = ExceptionUtils.getRootCauseMessage(ex);
	}
	
}
