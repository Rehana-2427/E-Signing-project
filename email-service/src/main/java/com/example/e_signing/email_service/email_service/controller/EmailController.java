package com.example.e_signing.email_service.email_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_signing.email_service.email_service.request.EmailRequest;
import com.example.e_signing.email_service.email_service.service.EmailService;

@RestController
@RequestMapping("/api/email")
//@CrossOrigin(origins = { "http://localhost:3003" })
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/sendotp")
	public ResponseEntity<String> sendOtp(@RequestBody EmailRequest request) {
		emailService.sendEmail(request);
		System.out.println("email controller");
		return ResponseEntity.ok("Email sent to " + request.getTo());
	}

	@PostMapping("/documentEmailSend")
	public ResponseEntity<String> sendEmails(@RequestBody EmailRequest request) {
		System.out.println("Email controller");
		try {
			System.out.println("Email controller-----2");
			emailService.sendDocumentEmails(request);
			
			return ResponseEntity.ok("Emails sent successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email sending failed.");
		}
	}
	
	
	@PostMapping("/groupDocumentEmailSend")
	public ResponseEntity<String> sendGroupDocumentEmails(@RequestBody EmailRequest request) {
		System.out.println("Email controller");
		try {
			System.out.println("Email controller-----2");
			emailService.sendGroupDocumentEmails(request);
			
			return ResponseEntity.ok("Emails sent successfully.");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Email sending failed.");
		}
	}
	

}
