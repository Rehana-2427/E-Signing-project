package com.example.document.service.Document_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.entity.Signer;
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

	@GetMapping("/documents-by-email")
	public ResponseEntity<List<MyDocumentDTO>> getDocumentsByEmail(@RequestParam String email) {
	    try {
	        List<MyDocumentDTO> documents = signerService.getDocumentsByEmail(email);
	        return ResponseEntity.ok(documents);
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


}
