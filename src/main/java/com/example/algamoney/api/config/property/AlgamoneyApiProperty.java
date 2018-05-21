package com.example.algamoney.api.config.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.Getter;

@Configuration
@ConfigurationProperties("algamoney")
public class AlgamoneyApiProperty {
	
	@Getter
	private final Seguranca seguranca = new Seguranca();
	
	@Getter
	private final CORS cors = new CORS();

	@Data 
	public static class Seguranca{
		private boolean enableHttps;		
	}
	
	@Data
	public static class CORS{
		private String[] enableOrigin;
	}
}
