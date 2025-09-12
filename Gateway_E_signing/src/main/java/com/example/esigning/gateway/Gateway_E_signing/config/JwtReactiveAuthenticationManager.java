package com.example.esigning.gateway.Gateway_E_signing.config;

import java.util.Collections;

import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {

	private final String jwtSecret;

	public JwtReactiveAuthenticationManager(String jwtSecret) {
		this.jwtSecret = jwtSecret;
	}

	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		// TODO Auto-generated method stub
		String token = authentication.getCredentials().toString();

		try {
			Claims claims = Jwts.parser().setSigningKey(jwtSecret.getBytes()).parseClaimsJws(token).getBody();

			String username = claims.getSubject();

			// Here, roles can be extracted from claims if added
			return Mono.just(new UsernamePasswordAuthenticationToken(username, null,
					Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")) // or from claims
			));

		} catch (Exception e) {
			return Mono.empty(); // JWT invalid
		}
	}

}
