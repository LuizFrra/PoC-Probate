package com.poc.jwt;

import com.poc.jwt.Security.JWT.JWTKeyGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class JwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}

	@Bean
	public JWTKeyGenerator generateRSA() throws Exception {
		JWTKeyGenerator JWTkeyGenerator = new JWTKeyGenerator();
		JWTkeyGenerator.setKeySize(512);
		JWTkeyGenerator.useRSA = true;
		JWTkeyGenerator.generateKey();
		return JWTkeyGenerator;
	}
}
