package com.example.e_signing_project.authservice.config;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
//	private final static String SECRET_KEY = SecretKeyGeneratorExample.generateSecretKey();
	// This is wrong for microservices because it generates a new key every time the
	// app starts —
	// meaning old tokens become invalid and gateway can’t validate them.

	@Value("${jwt.secret}")
	private String secretKey;
	private final static int EXPIRATION_TIME = 1000 * 60 * 60 * 6;

//	public static String generateToken(String email) {
//		return Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
//				.signWith(SignatureAlgorithm.HS512, SECRET_KEY).compact();
//	}
	public String generateToken(String email) {
		return Jwts.builder().setSubject(email).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();
	}
}
