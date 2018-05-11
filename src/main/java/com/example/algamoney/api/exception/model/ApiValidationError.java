package com.example.algamoney.api.exception.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApiValidationError extends ApiSubError{
	private String object;
	private String field;
	private Object rejectedValue;
	private String message;
}
