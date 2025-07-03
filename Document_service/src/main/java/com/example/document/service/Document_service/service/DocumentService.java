package com.example.document.service.Document_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Sign_Document;

public interface DocumentService {


	Document saveDocument(String title, String termsType, String termsLink, MultipartFile pdfFile,
			MultipartFile termsPdfFile, String signersJson, String senderEmail, LocalDate signRequiredBy);

	List<Document> getDocumentByUserEmail(String userEmail);

	Document findById(Long documentId);

	Sign_Document uploadSignedDocument(MultipartFile file, String senderEmail, String userEmail);

	List<Sign_Document> getSignDocumentByUserEmail(String userEmail);

	Sign_Document downloadSignDocument(Long id);

}
