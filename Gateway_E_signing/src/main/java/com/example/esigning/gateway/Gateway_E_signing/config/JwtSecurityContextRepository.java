package com.example.esigning.gateway.Gateway_E_signing.config;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

public class JwtSecurityContextRepository implements ServerSecurityContextRepository {
	private final JwtReactiveAuthenticationManager authenticationManager;

	public JwtSecurityContextRepository(String jwtSecret) {
		this.authenticationManager = new JwtReactiveAuthenticationManager(jwtSecret);
	}

	@Override
	public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
		// TODO Auto-generated method stub
		return Mono.empty();
	}

	@Override
	public Mono<SecurityContext> load(ServerWebExchange exchange) {
		// TODO Auto-generated method stub
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			String authToken = authHeader.substring(7);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(authToken, authToken);

			return authenticationManager.authenticate(auth).map(SecurityContextImpl::new);
		}

		return Mono.empty();
	}

}
