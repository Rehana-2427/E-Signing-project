package com.example.adminservice.adminservice.fiegnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.adminservice.adminservice.dto.EmailCreditResponse;

@FeignClient(name = "email-service", path = "/api/email")
public interface EmailCreditsClient {

	@PostMapping("/sendAssignedCreditsEmail")
	ResponseEntity<String> sendCreditsEmail(@RequestBody EmailCreditResponse emailCreditResponse);
}
