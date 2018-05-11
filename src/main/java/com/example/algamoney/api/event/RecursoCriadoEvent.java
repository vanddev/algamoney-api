package com.example.algamoney.api.event;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationEvent;

import lombok.EqualsAndHashCode;
import lombok.Value;

@SuppressWarnings("serial")
@Value
@EqualsAndHashCode(callSuper = false)
public class RecursoCriadoEvent extends ApplicationEvent{

	private HttpServletResponse response;
	private Long codigo;

	public RecursoCriadoEvent(Object source, HttpServletResponse response, Long codigo) {
		super(source);
		this.response = response;
		this.codigo = codigo;
	}
}
