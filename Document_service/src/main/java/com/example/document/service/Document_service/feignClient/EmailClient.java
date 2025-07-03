package com.example.document.service.Document_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.document.service.Document_service.entity.EmailRequest;


@FeignClient(name = "email-service", path = "/api/email")
public interface EmailClient {
	
	@PostMapping("/DocumentEmailSend")
	void sendDocumentSigningRequestEmail(@RequestBody EmailRequest emailRequest);

}
