package com.example.e_signing_project.authservice.config;

import java.sql.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	// Get the dynamically generated secret key
	private final static String SECRET_KEY = SecretKeyGeneratorExample.generateSecretKey();

	//private final int EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour
	private final static int EXPIRATION_TIME = 1000 * 60 * 60 * 6; // 6 hours

	public static String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
	}
}
