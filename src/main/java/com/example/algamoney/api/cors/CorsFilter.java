package com.example.algamoney.api.cors;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.algamoney.api.config.property.AlgamoneyApiProperty;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter{
	
	private final String OPTIONS = "OPTIONS";
	private final String ORIGIN = "origin";
	
	@Autowired
	private AlgamoneyApiProperty algamoneyApiProperty;

	//Filtra a requisição OPTIONS do browser que não consegue chegar naturalmente graças ao OAuth2
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		
		String reqOrigin = req.getHeader(ORIGIN);
		resp.setHeader("Access-Control-Allow-Credentials", "true");			
		if(OPTIONS.equals(req.getMethod()) && Arrays.asList(algamoneyApiProperty.getCors().getEnableOrigin()).contains(reqOrigin)) {
			resp.setHeader("Access-Control-Allow-Origin", reqOrigin);
			resp.setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT, OPTIONS");
			resp.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type, Accept");
			resp.setHeader("Access-Control-Max-Age", "3600");
			resp.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}


	@Override
	public void destroy() {}
	
}
