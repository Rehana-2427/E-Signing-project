package com.example.document.service.Document_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.service.SignerService;

@RestController
@RequestMapping("/api/signer")
public class SignerController {

	@Autowired
	private SignerService signerService;

	@PutMapping("/update-status")
	public ResponseEntity<String> updateSignerStatus(@RequestBody SignerRequest request) {
		try {
			signerService.updateSignerStatus(request);
			return ResponseEntity.ok("Signer status updated successfully.");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update signer status.");
		}
	}

}
