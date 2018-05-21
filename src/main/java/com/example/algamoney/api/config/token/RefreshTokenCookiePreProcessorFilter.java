package com.example.algamoney.api.config.token;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class RefreshTokenCookiePreProcessorFilter implements Filter{
	
	private final String TOKEN_REQUEST_PATH = "/oauth/token";
	private final String GRANT_TYPE = "refresh_token";
	
	
	//Antes de seguir o fluxo de autenticacao, captura o refresh token do cookie e adicina-o ao body
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		
		if(TOKEN_REQUEST_PATH.equals(req.getRequestURI()) && GRANT_TYPE.equals(req.getParameter("grant_type")) && req.getCookies() != null) {
			List<Cookie> cookieList = Arrays.asList(req.getCookies());
			try {
				String refreshToken  = cookieList.stream().filter(cookie -> cookie.getName().equals("refreshToken")).findFirst().map(Cookie::getValue).get();
				req = new ApiServletRequestWrapper(req, refreshToken);				
			}finally {}
		}
		
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}
	
	static class ApiServletRequestWrapper extends HttpServletRequestWrapper{

		private String refreshToken;
		public ApiServletRequestWrapper(HttpServletRequest request, String refreshToken) {
			super(request);
			this.refreshToken = refreshToken;
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> map = new ParameterMap<>(getRequest().getParameterMap());
			map.put("refresh_token", new String[] {refreshToken});
			map.setLocked(true);
			return map;
		}
	}

}
