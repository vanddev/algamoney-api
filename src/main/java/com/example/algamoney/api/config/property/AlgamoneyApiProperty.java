package com.example.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix="algamoney")
@Getter
@Setter
public class AlgamoneyApiProperty {
	
	private String test;
	private final Seguranca seguranca = new Seguranca();
	private final CORS cors = new CORS();

	@Getter
	@Setter
	public static class Seguranca{
		private boolean enableHttps;		
	}
	
	@Getter
	@Setter
	public static class CORS{
		private String[] enableOrigin;
	}
}
