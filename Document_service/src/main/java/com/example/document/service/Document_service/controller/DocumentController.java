package com.example.document.service.Document_service.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.example.document.service.Document_service.dto.DocumentRequest;
import com.example.document.service.Document_service.dto.MyConsentResponse;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.service.DocumentService;

@RestController
@RequestMapping("/api/documents")
//@CrossOrigin(origins = { "http://localhost:3003" })
public class DocumentController {

	@Autowired
	private DocumentService documentService;

	@PostMapping("/save-document")
	public ResponseEntity<String> saveDocument(@RequestPart("data") DocumentRequest data,
			@RequestPart("file") MultipartFile file) throws IOException {
		Long savedId = documentService.saveDocument(data, file);
		return ResponseEntity.ok("Document saved with ID: " + savedId);
	}

	@GetMapping("/my-consents")
	public ResponseEntity<List<MyConsentResponse>> getMyConsents(@RequestParam String senderEmail) {
		List<MyConsentResponse> myConsents = documentService.getMyConsentsBySender(senderEmail);
		return ResponseEntity.ok(myConsents);
	}

	@GetMapping("/view-document/{documentId}")
	public ResponseEntity<byte[]> viewDocument(@PathVariable Long documentId) {
		System.out.println(documentId);
		Document document = documentService.getDocumentById(documentId);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename(document.getFileName()).build());

		return ResponseEntity.ok().headers(headers).body(document.getEditedFile());
	}
}
