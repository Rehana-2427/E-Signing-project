package com.example.document.service.Document_service.jwttoken;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private final static String SECRET_KEY = SecretKeyGenerator.generateSecretKey();

	public static String generateToken(String senderName, String documentName, String recipientEmail, Long id, Date deadline) {
		return Jwts.builder().claim("senderName", senderName).claim("documentName", documentName)
				.claim("recipientEmail", recipientEmail).claim("documentId",id).setExpiration(deadline) 
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
	}

}
