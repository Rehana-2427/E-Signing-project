package com.example.e_signing.email_service.email_service.service;

import org.springframework.stereotype.Service;

@Service
public interface EmailService {

	void sendEmail(String to, String subject, String body);


//	String sendotp(String userEmail);
}
