package com.companyService.company_service.jwttoken;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



public class JwtUtil {

	private final static String SECRET_KEY = SecretKeyGenerator.generateSecretKey();

	public static String generateToken(String companyName, String invitedByUserEmail, String userEmail) {
		long expirationTime = 1000 * 60 * 60 * 24; 
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + expirationTime);

		return Jwts.builder().claim("companyName", companyName).claim("invitedBy", invitedByUserEmail)
				.claim("userEmail", userEmail).setIssuedAt(now).setExpiration(expiryDate)
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes()).compact();
	}
}


