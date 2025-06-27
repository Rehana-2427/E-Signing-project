package com.example.e_signing_project.authservice.service;

import org.springframework.stereotype.Service;

@Service
public interface otpService {

	int sendingOtp(String userEmail);

}
