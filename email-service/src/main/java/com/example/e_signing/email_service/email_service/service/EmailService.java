package com.example.e_signing.email_service.email_service.service;

import org.springframework.stereotype.Service;

import com.example.e_signing.email_service.email_service.request.EmailRequest;

@Service
public interface EmailService {

	public void sendEmail(EmailRequest request);


	public void sendDocumentEmails(EmailRequest request) throws Exception;
}
