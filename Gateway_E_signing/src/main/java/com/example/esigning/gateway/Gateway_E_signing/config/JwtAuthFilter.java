package com.example.esigning.gateway.Gateway_E_signing.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthFilter implements GlobalFilter, Ordered {

	@Value("${jwt.secret}")
	private String jwtSecret;
	private static final String[] WHITELIST = { "/authservice/", "/email-service/", "/documentservice/",
			"/adminservice/","/companyService/","/chat-service/"};

	private boolean isWhitelisted(String path) {
		for (String allowedPath : WHITELIST) {
			if (path.startsWith(allowedPath)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		String path = exchange.getRequest().getURI().getPath();

		if (isWhitelisted(path)) {
			return chain.filter(exchange);
		}
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}

		String token = authHeader.substring(7);

		try {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();

			ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
					.header("X-User-Email", claims.getSubject()).build();

			return chain.filter(exchange.mutate().request(mutatedRequest).build());

		} catch (Exception e) {
			exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
			return exchange.getResponse().setComplete();
		}
	}

	@Override
	public int getOrder() {
		// TODO Auto-generated method stub
		return -1;
	}

}
