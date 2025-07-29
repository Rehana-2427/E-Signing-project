package com.example.e_signing_project.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_signing_project.authservice.service.otpService;

@RestController
@RequestMapping("/otp")
public class otpController {

	@Autowired
	private otpService otpService;
	
	@GetMapping("/validateUserEmail")
	public ResponseEntity<?> sendOTP(@RequestParam String userEmail) {
		int otpResult = otpService.sendingOtp(userEmail);
		return new ResponseEntity<>(otpResult, HttpStatus.OK);
	}

}
