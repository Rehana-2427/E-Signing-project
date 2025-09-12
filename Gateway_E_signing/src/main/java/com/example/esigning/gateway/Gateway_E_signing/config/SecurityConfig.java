package com.example.esigning.gateway.Gateway_E_signing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

	@Value("${jwt.secret}")
	private String jwtSecret;

	@Bean
	public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
		return http.csrf().disable().authorizeExchange()
				.pathMatchers("/authservice/**", "/documentservice/**", "/adminservice/**", "/email-service/**")
				.permitAll().anyExchange().authenticated().and()
				.authenticationManager(new JwtReactiveAuthenticationManager(jwtSecret))
				.securityContextRepository(new JwtSecurityContextRepository(jwtSecret)).build();
	}
}
