package com.example.document.service.Document_service.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = { "http://localhost:3003" })
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping("/save-document")
	public ResponseEntity<Document> uploadDocument(@RequestParam("title") String title,
			@RequestParam("signRequiredBy") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate signRequiredBy,
			@RequestParam("termsType") String termsType,
			@RequestParam(value = "termsLink", required = false) String termsLink,
			@RequestParam(value = "pdf", required = false) MultipartFile pdfFile,
			@RequestParam(value = "termsPdf", required = false) MultipartFile termsPdfFile,
			@RequestParam("signers") String signersJson, @RequestParam("senderEmail") String senderEmail) {
		Document result = documentService.saveDocument(title, termsType, termsLink, pdfFile, termsPdfFile, signersJson,
				senderEmail, signRequiredBy);
		return new ResponseEntity<Document>(result, HttpStatus.OK);
	}

	
	
}
