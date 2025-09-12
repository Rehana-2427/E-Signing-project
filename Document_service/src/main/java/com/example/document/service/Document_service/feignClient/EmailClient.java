package com.example.document.service.Document_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.ReportRequestDto;

@FeignClient(name = "email-service", path = "/api/email")
public interface EmailClient {

	@PostMapping("/documentEmailSend")
	void sendDocumentSigningRequestEmail(@RequestBody EmailRequest emailRequest);

	@PostMapping("/groupDocumentEmailSend")
	void sendGroupDocumentSigningRequestEmail(EmailRequest emailRequest);

	@PostMapping("/sendUpdateStatusEmail")
	void sendUpdateStatusEmail(EmailRequest emailRequest);

	@PostMapping("/sendSummaryEmail")
	void sendSummaryEmail(EmailRequest payload);

	@PostMapping("/sendUpdateStatusEmailToAll")
	void sendUpdateStatusEmailToAll(EmailRequest emailRequest);

	@PostMapping("/send-reminder")
	void sendReminder(EmailRequest request);

	@PostMapping("/send-report")
	void sendReport(@RequestBody EmailRequest emailRequest);
	
	@PostMapping("/send-report")
    ResponseEntity<String> sendSigningReport(@RequestBody ReportRequestDto reportRequestDto);

}
