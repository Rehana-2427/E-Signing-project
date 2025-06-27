package com.example.e_signing.email_service.email_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
@CrossOrigin(origins = { "http://localhost:3003" })
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping("/send")
	public ResponseEntity<String> send(@RequestBody EmailRequest request) {
		emailService.sendEmail(request.getTo(), request.getSubject(), request.getBody());
		return ResponseEntity.ok("Email sent to " + request.getTo());
	}
}
