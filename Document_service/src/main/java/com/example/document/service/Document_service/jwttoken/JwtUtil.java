package com.example.document.service.Document_service.jwttoken;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	private final static String SECRET_KEY = SecretKeyGenerator.generateSecretKey();

	public static String generateToken(String senderName, String documentName, String recipientEmail, Long id,
			Date deadline) {
		return Jwts.builder().claim("senderName", senderName).claim("documentName", documentName)
				.claim("recipientEmail", recipientEmail).claim("documentId", id).setExpiration(deadline)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
	}

	public static String generateReviewToken(String senderEmail, String senderName, String documentName,
			String reviewerEmail, String companyName) {
		var builder = Jwts.builder().claim("senderEmail", senderEmail).claim("senderName", senderName)
				.claim("documentName", documentName).claim("reviewerEmail", reviewerEmail);
		if (companyName != null && !companyName.trim().isEmpty()) {
			builder.claim("companyName", companyName);
		}

		return builder.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
	}
}