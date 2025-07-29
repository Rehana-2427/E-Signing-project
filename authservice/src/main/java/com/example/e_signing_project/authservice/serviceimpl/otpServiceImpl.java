package com.example.e_signing_project.authservice.serviceimpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e_signing_project.authservice.fiegnclient.EmailClient;
import com.example.e_signing_project.authservice.model.EmailRequest;
import com.example.e_signing_project.authservice.repository.UserRepository;
import com.example.e_signing_project.authservice.service.otpService;

@Service
public class otpServiceImpl implements otpService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailClient emailClient;

	private Map<String, Integer> otpStorage = new HashMap<>();

	@Override
	public int sendingOtp(String userEmail) {
		try {
			// Generate OTP
			Random random = new Random();
			int otp = 100000 + random.nextInt(900000);
			System.out.println("Generated OTP for " + userEmail + ": " + otp);
			otpStorage.put(userEmail, otp);

			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setTo(userEmail);
			emailRequest.setOtp(String.valueOf(otp));
			emailClient.sendEmail(emailRequest);

			return otp;

		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}