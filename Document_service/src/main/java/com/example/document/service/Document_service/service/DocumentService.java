package com.example.document.service.Document_service.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.dto.AuditTrailResponseDto;
import com.example.document.service.Document_service.dto.DocumentRequest;
import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.MyConsentResponse;
import com.example.document.service.Document_service.entity.Document;

//
//import java.time.LocalDate;
//import java.util.List;
//
//import org.springframework.web.multipart.MultipartFile;
//
//import com.example.document.service.Document_service.dto.DocumentStatusDTO;
//import com.example.document.service.Document_service.entity.Document;
//import com.example.document.service.Document_service.entity.Sign_Document;
//
public interface DocumentService {

	Long saveDocument(DocumentRequest data, MultipartFile file) throws IOException;
//
//
//	Document saveDocument(String title, String termsType, String termsLink, MultipartFile pdfFile,
//			MultipartFile termsPdfFile, String signersJson, String senderEmail, LocalDate signRequiredBy);
//
//	List<Document> getDocumentByUserEmail(String userEmail);
//
//	Document findById(Long documentId);
//
//	Sign_Document uploadSignedDocument(MultipartFile file, String senderEmail, String userEmail);
//
//	List<Sign_Document> getSignDocumentByUserEmail(String userEmail);
//
//	Sign_Document downloadSignDocument(Long id);
//
//	List<DocumentStatusDTO> getDocumentStatusBySender(String senderEmail);
//

	List<MyConsentResponse> getMyConsentsBySender(String senderEmail);

	Map<String, String> getDocumentInfo(Long docId, Long sid);

	Document getDocumentById(Long documentId);

	ResponseEntity<byte[]> getDocumentForSigner(Long documentId, String email);

	List<MyConsentResponse> getDraftConsentsBySender(String senderEmail);

	void processReminderRequest(EmailRequest request);

	Integer getTotalCreditsBySenderEmail(String senderEmail);

	void checkAndSendReportIfAllSigned(Long documentId);


	AuditTrailResponseDto getAuditTrailByDocumentId(Long documentId);
}
