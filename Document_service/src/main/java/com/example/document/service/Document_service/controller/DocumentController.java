package com.example.document.service.Document_service.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Sign_Document;
import com.example.document.service.Document_service.service.DocumentService;
import org.springframework.web.bind.annotation.GetMapping;


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

	@GetMapping("/getDocument")
	public ResponseEntity<List<Document>> getDocument(@RequestParam String userEmail) {
		return new ResponseEntity<List<Document>>( documentService.getDocumentByUserEmail(userEmail),HttpStatus.OK);
	}
	
	@GetMapping("/documentspdf")
	public ResponseEntity<byte[]> downloadPdf(@RequestParam Long documentId) {
	    Document doc = documentService.findById(documentId); // or however you get it
	    byte[] pdfData = doc.getPdf();

	    return ResponseEntity.ok()
	        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getTitle() + ".pdf\"")
	        .contentType(MediaType.APPLICATION_PDF)
	        .body(pdfData);
	}
	
	@PostMapping("/save-sign-document")
	public ResponseEntity<Sign_Document> uploadSignedDocument(
	        @RequestParam("file") MultipartFile file,
	        @RequestParam("senderEmail") String senderEmail,
	        @RequestParam("userEmail") String userEmail
	) {
		Sign_Document uploadSignedDocument = documentService.uploadSignedDocument(file,senderEmail,userEmail);
	    // Handle saving logic
	    System.out.println("Sender: " + senderEmail);
	    System.out.println("Signed by: " + userEmail);
	    return ResponseEntity.ok(uploadSignedDocument);
	}
	
	@GetMapping("/getSignDocument")
	public ResponseEntity<List<Sign_Document>> getSignDocument(@RequestParam String userEmail) {
		return new ResponseEntity<List<Sign_Document>>( documentService.getSignDocumentByUserEmail(userEmail),HttpStatus.OK);
	}
	
	@GetMapping("/downloadSignedPdf")
	public ResponseEntity<byte[]> downloadSignedPdf(@RequestParam Long id) {
	   Sign_Document doc=documentService.downloadSignDocument(id);
	  
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_PDF);
	        headers.setContentDisposition(ContentDisposition.attachment()
	            .filename("signed_document_" + id + ".pdf")
	            .build());
	        return new ResponseEntity<>(doc.getSignDocument(), headers, HttpStatus.OK);
	    
	}

}
