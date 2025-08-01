package com.example.document.service.Document_service.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

//
//import java.io.IOException;
//import java.time.LocalDate;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.ExceptionHandler.DocumentNotFoundException;
import com.example.document.service.Document_service.dto.DocumentRequest;
import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.MyConsentResponse;
import com.example.document.service.Document_service.dto.RecipientToken;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.dto.UserDTO;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.feignClient.AuthServiceClient;
import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.jwttoken.JwtUtil;
//
//import com.example.document.service.Document_service.dto.DocumentStatusDTO;
//import com.example.document.service.Document_service.entity.Document;
//
//import com.example.document.service.Document_service.entity.Sign_Document;
//
//import com.example.document.service.Document_service.entity.EmailRequest;
//
//import com.example.document.service.Document_service.entity.Signer;
//import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.repo.DocumentRepository;
//import com.example.document.service.Document_service.repo.Sign_DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.DocumentService;

//import com.fasterxml.jackson.databind.ObjectMapper;
//
@Service
public class DocumentServiceImpl implements DocumentService {
//
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private SignerRepository signerRepository;

	@Autowired
	private EmailClient emailClient;

	@Autowired
	private AuthServiceClient authServiceClient;

	@Override
	public Long saveDocument(DocumentRequest data, MultipartFile file) throws IOException {
		Document doc = new Document();
		doc.setSenderEmail(data.getSenderEmail());
		doc.setSenderName(data.getSenderName());
		doc.setDocumentName(data.getDocumentName());
		doc.setDescription(data.getDescription());
		doc.setFileName(data.getFileName());
		doc.setSigningMode(data.getSigningMode());
		doc.setAdditionalInitials(data.getAdditionalInitials());
		doc.setDeadline(data.getDeadline());

		doc.setReminderEveryDay(data.getReminderEveryDay());
		doc.setReminderDaysBeforeEnabled(data.getReminderDaysBeforeEnabled());
		doc.setReminderDaysBefore(data.getReminderDaysBefore());
		doc.setReminderLastDay(data.getReminderLastDay());
		doc.setSendFinalCopy(data.getSendFinalCopy());

		doc.setTotalCredits(
				(data.getTotalCredits() == null || data.getTotalCredits() == 0) ? 5 : data.getTotalCredits());
		doc.setDocumentCharge(
				(data.getDocumentCharge() == null || data.getDocumentCharge() == 0) ? 5 : data.getDocumentCharge());
		doc.setSignatoryCharge(data.getSignatoryCharge());

		doc.setEditedFile(file.getBytes());
		doc.setDraft(data.getDraft() != null ? data.getDraft() : false);

		Document savedDoc = documentRepository.save(doc);

		List<RecipientToken> recipientTokens = new ArrayList<>();
		for (SignerRequest s : data.getSigners()) {

			Signer signer = new Signer();
			signer.setName(s.getName());
			signer.setEmail(s.getEmail());
			signer.setDocument(savedDoc);
			signerRepository.save(signer);
			String jwtToken = JwtUtil.generateToken(data.getSenderName(), data.getDocumentName(), s.getEmail(),
					doc.getId(), java.sql.Date.valueOf(data.getDeadline()) // convert LocalDate to Date
			);

			recipientTokens.add(new RecipientToken(s.getName(), s.getEmail(), doc.getId(), jwtToken));
		}

		// ✅ Skip sending email if it's a draft
		if (data.getDraft() != null && data.getDraft()) {
			return savedDoc.getId();
		}

		// Not a draft - send email
		List<String> recipientEmails = data.getSigners().stream().map(SignerRequest::getEmail)
				.collect(Collectors.toList());

		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setSenderEmail(data.getSenderEmail());
//		emailRequest.setRecipientEmails(recipientEmails);
		emailRequest.setRecipients(recipientTokens);
		emailRequest.setTitle("Please sign the document: " + data.getDocumentName());
		emailRequest.setSignRequiredBy(data.getDeadline());
		emailRequest.setSenderName(data.getSenderName());
		emailClient.sendDocumentSigningRequestEmail(emailRequest);

		return savedDoc.getId();
	}

	@Override
	public List<MyConsentResponse> getMyConsentsBySender(String senderEmail) {
		List<Document> documents = documentRepository.findBySenderEmailOrderByCreatedDateDesc(senderEmail);

		List<MyConsentResponse> responseList = new ArrayList();

		for (Document doc : documents) {
			int totalSigners = doc.getSigners().size();
			int signedCount = (int) doc.getSigners().stream().filter(s -> "Signed".equalsIgnoreCase(s.getSignStatus()))
					.count();

			// Calculate signedOn only if all signed
			LocalDate signedOn = null;
			if (totalSigners > 0 && signedCount == totalSigners) {
				signedOn = doc.getSigners().stream().map(Signer::getSignedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}

			responseList.add(new MyConsentResponse(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(),
					doc.getDraft(), signedOn, totalSigners, signedCount));
		}

		return responseList;
	}

	@Override
	public Map<String, String> getDocumentInfo(Long docId, Long sid) {
		Document document = documentRepository.findById(docId)
				.orElseThrow(() -> new RuntimeException("Document not found"));

		Signer signer = signerRepository.findById(sid).orElseThrow(() -> new RuntimeException("Signer not found"));

		UserDTO user = authServiceClient.getUserByEmail(document.getSenderEmail());

		Map<String, String> response = new HashMap();
		response.put("senderName", user.getUserName());
		response.put("documentName", document.getDocumentName());

		return response;
	}

	@Override
	public Document getDocumentById(Long documentId) {
		// TODO Auto-generated method stub
		 return documentRepository.findById(documentId)
	                .orElseThrow(() -> new DocumentNotFoundException("Document not found with ID: " + documentId));
	    
	}
}
