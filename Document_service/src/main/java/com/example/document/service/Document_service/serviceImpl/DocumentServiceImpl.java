package com.example.document.service.Document_service.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
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
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.document.service.Document_service.ExceptionHandler.DocumentNotFoundException;
import com.example.document.service.Document_service.dto.AuditTrailResponseDto;
import com.example.document.service.Document_service.dto.DocumentRequest;
import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.MyConsentResponse;
import com.example.document.service.Document_service.dto.ParticipantStatusDTO;
import com.example.document.service.Document_service.dto.RecipientToken;
import com.example.document.service.Document_service.dto.ReportRequestDto;
import com.example.document.service.Document_service.dto.ReviewerToken;
import com.example.document.service.Document_service.dto.ReviewrRequest;
import com.example.document.service.Document_service.dto.SignerInfo;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.dto.UnseenDocumentDTO;
import com.example.document.service.Document_service.dto.UserDTO;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Reviewer;
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
import com.example.document.service.Document_service.repo.ReviewerRepo;
//import com.example.document.service.Document_service.repo.Sign_DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.DocumentService;
import com.example.document.service.Document_service.utility.PdfUtils;

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
	private ReviewerRepo reviewerRepo;

	@Autowired
	private EmailClient emailClient;

	@Autowired
	private AuthServiceClient authServiceClient;

	@Autowired
	private ReviewerServiceImpl reviewerService;
//	@Override
//	public Long saveDocument(DocumentRequest data, MultipartFile file) throws IOException {
//		Document doc = new Document();
//
//		if (data.getDocumentId() != null) {
//			doc = documentRepository.findById(data.getDocumentId())
//					.orElseThrow(() -> new RuntimeException("Document not found with ID: " + data.getDocumentId()));
//		}
//
//		if (data.getCompanyName() != null && !data.getCompanyName().isEmpty()) {
//			doc.setCompanyName(data.getCompanyName());
//		}
//
//		doc.setSenderEmail(data.getSenderEmail());
//		doc.setSenderName(data.getSenderName());
//		doc.setDocumentName(data.getDocumentName());
//		doc.setDescription(data.getDescription());
//		doc.setFileName(data.getFileName());
//		doc.setSigningMode(data.getSigningMode());
//		doc.setDeadline(data.getDeadline());
//		doc.setOn_The_Side(data.getOn_The_Side());
//		doc.setOn_The_Bottom(data.getOn_The_Bottom());
//		doc.setReminderEveryDay(data.getReminderEveryDay());
//		doc.setReminderDaysBeforeEnabled(data.getReminderDaysBeforeEnabled());
//		doc.setReminderDaysBefore(data.getReminderDaysBefore());
//		doc.setReminderLastDay(data.getReminderLastDay());
//		doc.setSendFinalCopy(data.getSendFinalCopy());
//
//		doc.setTotalCredits(data.getTotalCredits());
//		doc.setDocumentCharge(data.getDocumentCharge());
//		doc.setSignatoryCharge(data.getSignatoryCharge());
//
//		doc.setEditedFile(file.getBytes());
//		doc.setDraft(data.getDraft() != null ? data.getDraft() : false);
//
//		// Save document
//		Document savedDoc = documentRepository.save(doc);
//
//		List<ReviewerToken> reviewerTokens = new ArrayList<>();
//
//		if (data.getReviewers() != null && !data.getReviewers().isEmpty()) {
//			doc.setIsReviewerAdded(true);
//
//			for (ReviewrRequest r : data.getReviewers()) {
//				Reviewer reviewer = new Reviewer();
//				reviewer.setReviewerEmail(r.getReviewerEmail());
//				reviewer.setApproved(false);
//				reviewer.setDocument(savedDoc); // associate with the saved document
//				reviewerRepo.save(reviewer);
//
//				// Generate reviewer token
//				String jwtToken = JwtUtil.generateReviewToken(data.getSenderEmail(), data.getSenderName(),
//						data.getDocumentName(), r.getReviewerEmail(), data.getCompanyName());
//
//				reviewerTokens.add(new ReviewerToken(r.getReviewerEmail(), doc.getId(), jwtToken));
//			}
//		} else {
//			doc.setIsReviewerAdded(false);
//		}
//
//		// Save signers
//		List<RecipientToken> recipientTokens = new ArrayList<>();
//		for (SignerRequest s : data.getSigners()) {
//			Signer signer = new Signer();
//			signer.setName(s.getName());
//			signer.setEmail(s.getEmail());
//			signer.setDocument(savedDoc);
//			signerRepository.save(signer);
//
//			String jwtToken = JwtUtil.generateToken(data.getSenderName(), data.getDocumentName(), s.getEmail(),
//					doc.getId(), java.sql.Date.valueOf(data.getDeadline()));
//
//			recipientTokens.add(new RecipientToken(s.getName(), s.getEmail(), doc.getId(), jwtToken));
//		}
//
//		// Send reviewer email even if it's a draft
//		List<String> reviewerEmailList = data.getReviewers() != null
//				? data.getReviewers().stream().map(ReviewrRequest::getReviewerEmail).collect(Collectors.toList())
//				: Collections.emptyList();
//
//		if (!reviewerEmailList.isEmpty()) {
//			EmailRequest reviewerEmailRequest = new EmailRequest();
//			reviewerEmailRequest.setSenderEmail(data.getSenderEmail());
//			reviewerEmailRequest.setReviewers(reviewerTokens);
//			reviewerEmailRequest.setTitle("Please review the document: " + data.getDocumentName());
//			reviewerEmailRequest.setSenderName(data.getSenderName());
//
//			emailClient.sendDocumentReviewRequestEmail(reviewerEmailRequest);
//		}
//
//		// If draft, return early without sending emails to signers
//		if (Boolean.TRUE.equals(data.getDraft())) {
//			return savedDoc.getId();
//		}
//
//		// Not a draft — send emails to signers
//		List<String> recipientEmails = data.getSigners().stream().map(SignerRequest::getEmail)
//				.collect(Collectors.toList());
//
//		EmailRequest emailRequest = new EmailRequest();
//		emailRequest.setSenderEmail(data.getSenderEmail());
//		emailRequest.setRecipients(recipientTokens);
//		emailRequest.setTitle("Please sign the document: " + data.getDocumentName());
//		emailRequest.setSignRequiredBy(data.getDeadline());
//		emailRequest.setSenderName(data.getSenderName());
//
//		emailClient.sendDocumentSigningRequestEmail(emailRequest);
//
//		return savedDoc.getId();
//	}
//

	@Override
	public Long saveDocument(DocumentRequest data, MultipartFile file) throws IOException {
		Document doc = new Document();

		// Load existing document if ID exists
		if (data.getDocumentId() != null) {
			doc = documentRepository.findById(data.getDocumentId())
					.orElseThrow(() -> new RuntimeException("Document not found with ID: " + data.getDocumentId()));
		}

		// Set document fields
		doc.setSenderEmail(data.getSenderEmail());
		doc.setSenderName(data.getSenderName());
		doc.setDocumentName(data.getDocumentName());
		doc.setDescription(data.getDescription());
		doc.setFileName(data.getFileName());
		doc.setSigningMode(data.getSigningMode());
		doc.setDeadline(data.getDeadline());
		doc.setOn_The_Side(data.getOn_The_Side());
		doc.setOn_The_Bottom(data.getOn_The_Bottom());
		doc.setReminderEveryDay(data.getReminderEveryDay());
		doc.setReminderDaysBeforeEnabled(data.getReminderDaysBeforeEnabled());
		doc.setReminderDaysBefore(data.getReminderDaysBefore());
		doc.setReminderLastDay(data.getReminderLastDay());
		doc.setSendFinalCopy(data.getSendFinalCopy());
		doc.setTotalCredits(data.getTotalCredits());
		doc.setDocumentCharge(data.getDocumentCharge());
		doc.setSignatoryCharge(data.getSignatoryCharge());
		doc.setDraft(data.getDraft() != null ? data.getDraft() : false);

		// Handle reviewers
		if (data.getReviewers() != null && !data.getReviewers().isEmpty()) {
			doc.setIsReviewerAdded(true);
			doc.setReviewerCharge(data.getReviewers().size() * data.getReviewerCharge());
			doc.setDocumentStatus("IN_REVIEW");

			for (ReviewrRequest r : data.getReviewers()) {
				Reviewer reviewer = new Reviewer();
				reviewer.setReviewerEmail(r.getReviewerEmail());
				reviewer.setApproved(false);
				reviewer.setDocument(doc); // associate with document
				doc.getReviewers().add(reviewer); // cascading will save reviewer
			}
		} else {
			doc.setIsReviewerAdded(false);
			doc.setReviewerCharge(0);
			doc.setDocumentStatus("SENT_TO_SIGNERS");
		}

		// Set file content
		doc.setEditedFile(file.getBytes());

		// Handle signers
		if (data.getSigners() != null) {
			for (SignerRequest s : data.getSigners()) {
				Signer signer = new Signer();
				signer.setName(s.getName());
				signer.setEmail(s.getEmail());
				signer.setDocument(doc); // associate with document
				doc.getSigners().add(signer); // cascading will save signer
			}
		}

		// Save document (cascades reviewers and signers)
		Document savedDoc = documentRepository.save(doc);

		// If draft, do not send emails
		if (Boolean.TRUE.equals(savedDoc.getDraft())) {
			return savedDoc.getId();
		}

		// Send emails to reviewers if any
		if (savedDoc.getIsReviewerAdded()) {
			List<ReviewerToken> reviewerTokens = new ArrayList<>();
			for (Reviewer r : savedDoc.getReviewers()) {
				String jwtToken = JwtUtil.generateReviewToken(savedDoc.getSenderEmail(), savedDoc.getSenderName(),
						savedDoc.getDocumentName(), r.getReviewerEmail(), data.getCompanyName());
				reviewerTokens.add(new ReviewerToken(r.getReviewerEmail(), savedDoc.getId(), jwtToken));
			}

			EmailRequest reviewerEmailRequest = new EmailRequest();
			reviewerEmailRequest.setSenderEmail(savedDoc.getSenderEmail());
			reviewerEmailRequest.setReviewers(reviewerTokens);
			reviewerEmailRequest.setTitle("Please review the document: " + savedDoc.getDocumentName());
			reviewerEmailRequest.setSenderName(savedDoc.getSenderName());

			emailClient.sendDocumentReviewRequestEmail(reviewerEmailRequest);
		} else {
			sendEmailToSigners(savedDoc);
		}

		return savedDoc.getId();
	}

	private void sendEmailToSigners(Document document) {
		// Create recipient tokens for signers
		List<RecipientToken> recipientTokens = new ArrayList<>();
		if (document.getSigners() == null || document.getSigners().isEmpty()) {
			// No signers to send emails to, just return
			return;
		}
		for (Signer signer : document.getSigners()) {
			String jwtToken = JwtUtil.generateToken(document.getSenderName(), document.getDocumentName(),
					signer.getEmail(), document.getId(), java.sql.Date.valueOf(document.getDeadline()));

			recipientTokens.add(new RecipientToken(signer.getName(), signer.getEmail(), document.getId(), jwtToken));
		}

		// Prepare and send the email request to signers
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setSenderEmail(document.getSenderEmail());
		emailRequest.setRecipients(recipientTokens);
		emailRequest.setTitle("Please sign the document: " + document.getDocumentName());
		emailRequest.setSignRequiredBy(document.getDeadline());
		emailRequest.setSenderName(document.getSenderName());

		emailClient.sendDocumentSigningRequestEmail(emailRequest);
	}

	private String extractTextFromPdf(MultipartFile file) throws IOException {
		try (PDDocument document = PDDocument.load(file.getInputStream())) {
			PDFTextStripper stripper = new PDFTextStripper();
			return stripper.getText(document);
		}
	}

	@Override
	public List<MyConsentResponse> getMyConsentsBySender(String senderEmail) {
		List<Document> documents = documentRepository.findBySenderEmailAndDraftFalseOrderByCreatedDateDesc(senderEmail);

		List<MyConsentResponse> responseList = new ArrayList();

		for (Document doc : documents) {

			int totalreviewers = doc.getReviewers().size();
			int reviewrCount = (int) doc.getReviewers().stream().filter(Reviewer::getApproved).count();

			String reviewerStatus;
			if (totalreviewers == 0) {
				reviewerStatus = "No Reviewers";
			} else if (reviewrCount == totalreviewers) {
				reviewerStatus = "Approved";
			} else {
				reviewerStatus = "Pending";
			}

			String documentStatus;
			if (doc.getDocumentStatus() != null && doc.getDocumentStatus().equalsIgnoreCase("SENT_TO_SIGNERS")) {
				documentStatus = "SENT_TO_SIGNERS";
			} else if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				documentStatus = "REVIEW_COMPLETED";
			} else {
				documentStatus = "IN_REVIEW";
			}
			LocalDate reviewedOn = null;

			if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				reviewedOn = doc.getReviewers().stream().map(Reviewer::getApprovedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}
			int totalSigners = doc.getSigners().size();
			int signedCount = (int) doc.getSigners().stream()
					.filter(s -> "completed".equalsIgnoreCase(s.getSignStatus())).count();

			LocalDate signedOn = null;
			if (totalSigners > 0 && signedCount == totalSigners) {
				signedOn = doc.getSigners().stream().map(Signer::getSignedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}

			responseList.add(new MyConsentResponse(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(),
					doc.getDraft(), signedOn, totalSigners, signedCount, doc.getEditedFile(), reviewedOn,
					totalreviewers, reviewrCount, reviewerStatus, documentStatus));
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

	@Override
	public ResponseEntity<byte[]> getDocumentForSigner(Long documentId, String email) {
		// TODO Auto-generated method stub
		Signer signer = signerRepository.findByEmailAndDocumentId(email, documentId);

		if (signer == null) {
			throw new RuntimeException("Signer not found for this document and email.");
		}

		Document document = signer.getDocument();

		byte[] fileBytes;
		String filename;

		if (signer.getSignStatus() != null && ("completed".equalsIgnoreCase(signer.getSignStatus())
				|| "draft".equalsIgnoreCase(signer.getSignStatus())) && signer.getSigned_file() != null) {

			fileBytes = signer.getSigned_file();
			filename = "signed_" + document.getDocumentName() + ".pdf";
		} else {
			fileBytes = document.getEditedFile();
			filename = document.getDocumentName() + ".pdf";
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());

		return ResponseEntity.ok().headers(headers).body(fileBytes);
	}

	@Override
	public List<MyConsentResponse> getDraftConsentsBySender(String senderEmail) {
		List<Document> documents = documentRepository.findBySenderEmailAndDraftTrueOrderByCreatedDateDesc(senderEmail);

		List<MyConsentResponse> responseList = new ArrayList<>();

		for (Document doc : documents) {
			int totalreviewers = doc.getReviewers().size();
			int reviewrCount = (int) doc.getReviewers().stream().filter(Reviewer::getApproved).count();
			String reviewerStatus;
			if (totalreviewers == 0) {
				reviewerStatus = "No Reviewers";
			} else if (reviewrCount == totalreviewers) {
				reviewerStatus = "Approved";
			} else {
				reviewerStatus = "Pending";
			}
			String documentStatus;
			if (doc.getDocumentStatus() != null && doc.getDocumentStatus().equalsIgnoreCase("SENT_TO_SIGNERS")) {
				documentStatus = "SENT_TO_SIGNERS";
			} else if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				documentStatus = "REVIEW_COMPLETED";
			} else {
				documentStatus = "IN_REVIEW";
			}
			LocalDate reviewedOn = null;

			if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				reviewedOn = doc.getReviewers().stream().map(Reviewer::getApprovedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}
			int totalSigners = doc.getSigners().size();
			int signedCount = (int) doc.getSigners().stream()
					.filter(s -> "completed".equalsIgnoreCase(s.getSignStatus())).count();

			LocalDate signedOn = null;
			if (totalSigners > 0 && signedCount == totalSigners) {
				signedOn = doc.getSigners().stream().map(Signer::getSignedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}

			responseList.add(new MyConsentResponse(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(),
					doc.getDraft(), signedOn, totalSigners, signedCount, doc.getEditedFile(), reviewedOn,
					totalreviewers, reviewrCount, reviewerStatus, documentStatus));
		}

		return responseList;
	}

	@Override
	public void processReminderRequest(EmailRequest request) {
		// 1. Fetch document by ID
		Document document = documentRepository.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Document not found."));

		// 2. Generate JWT tokens for each recipient
		List<RecipientToken> recipientTokens = request.getRecipients().stream().map(recipient -> {
			String token = JwtUtil.generateToken(document.getSenderName(), document.getDocumentName(),
					recipient.getEmail(), document.getId(), java.sql.Date.valueOf(document.getDeadline()));

			return new RecipientToken(recipient.getName(), recipient.getEmail(), document.getId(), token);
		}).collect(Collectors.toList());

		// 3. Update request with enriched tokens
		request.setRecipients(recipientTokens);
		request.setTitle(document.getDocumentName());
		request.setSignRequiredBy(document.getDeadline());
		request.setSenderName(document.getSenderName());
		request.setSenderEmail(document.getSenderEmail());
		request.setPdfBytes(document.getEditedFile());

		// 4. Call email-service via FeignClient
		emailClient.sendReminder(request);
	}

	@Override
	public Integer getTotalCreditsBySenderEmail(String senderEmail) {
		// TODO Auto-generated method stub
		Integer sum = documentRepository.getTotalCreditsBySenderEmail(senderEmail);
		return sum != null ? sum : 0;
	}

	@Override
	public void checkAndSendReportIfAllSigned(Long documentId) {
		long unsignedCount = signerRepository.countUnsignedSigners(documentId);

		if (unsignedCount == 0) {
			Document document = documentRepository.findById(documentId)
					.orElseThrow(() -> new RuntimeException("Document not found"));

			List<Signer> signers = signerRepository.findByDocumentId(documentId);

			ReportRequestDto reportRequest = new ReportRequestDto();
			reportRequest.setDocumentName(document.getDocumentName());
			reportRequest.setSenderEmail(document.getSenderEmail());

			List<ReportRequestDto.SignerInfo> signerInfoList = signers.stream().map(signer -> {
				ReportRequestDto.SignerInfo info = new ReportRequestDto.SignerInfo();
				info.setName(signer.getName());
				info.setEmail(signer.getEmail());
				info.setSignedAt(signer.getSignedAt());
				return info;
			}).collect(Collectors.toList());

			reportRequest.setSignerList(signerInfoList);

			emailClient.sendSigningReport(reportRequest);
		}

	}

	public AuditTrailResponseDto getAuditTrailByDocumentId(Long documentId) {
		Document doc = documentRepository.findById(documentId)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Document not found"));

		List<Signer> signers = doc.getSigners();

		List<SignerInfo> signerDtos = signers.stream()
				.map(s -> new SignerInfo(s.getName(), s.getEmail(), s.getSignedAt(), s.getSignStatus()))
				.collect(Collectors.toList());

		boolean allCompleted = signers.stream()
				.allMatch(s -> s.getSignStatus() != null && s.getSignStatus().equalsIgnoreCase("completed"));

		AuditTrailResponseDto response = new AuditTrailResponseDto();
		response.setDocumentName(doc.getDocumentName());
		response.setSigners(signerDtos);
		response.setAllCompleted(allCompleted);
		return response;
	}

	@Override
	public List<MyConsentResponse> getSearchSentConsensts(String senderEmail, String query) {
		List<Document> documents = documentRepository.findBySenderEmailAndDraftFalseOrderByCreatedDateDesc(senderEmail);

//		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
//			documents = documents.stream()
//					.filter(doc -> doc.getDocumentName().toLowerCase().contains(query.toLowerCase()))
//					.collect(Collectors.toList());
//		}

		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
			String finalQuery = query.toLowerCase();

			documents = documents.stream().filter(doc -> {
				boolean nameMatch = doc.getDocumentName() != null
						&& doc.getDocumentName().toLowerCase().contains(finalQuery);

				boolean contentMatch = false;
				if (doc.getEditedFile() != null && doc.getEditedFile().length > 0) {
					String extractedText = PdfUtils.extractTextFromPdf(doc.getEditedFile());
					contentMatch = extractedText.toLowerCase().contains(finalQuery);
				}

				return nameMatch || contentMatch;
			}).collect(Collectors.toList());
		}

		return convertToResponseList(documents);
	}

//	@Override
//	public List<MyConsentResponse> getSearchDraftConsensts(String senderEmail, String query) {
//		List<Document> documents = documentRepository.findBySenderEmailAndDraftTrueOrderByCreatedDateDesc(senderEmail);
//
//		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
//			documents = documents.stream()
//					.filter(doc -> doc.getDocumentName().toLowerCase().contains(query.toLowerCase()))
//					.collect(Collectors.toList());
//		}
//
//		return convertToResponseList(documents);
//	}

	@Override
	public List<MyConsentResponse> getSearchDraftConsensts(String senderEmail, String query) {
		List<Document> documents = documentRepository.findBySenderEmailAndDraftTrueOrderByCreatedDateDesc(senderEmail);

		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
			String finalQuery = query.toLowerCase();

			documents = documents.stream().filter(doc -> {
				boolean nameMatch = doc.getDocumentName() != null
						&& doc.getDocumentName().toLowerCase().contains(finalQuery);

				boolean contentMatch = false;
				if (doc.getEditedFile() != null && doc.getEditedFile().length > 0) {
					String extractedText = PdfUtils.extractTextFromPdf(doc.getEditedFile());
					contentMatch = extractedText.toLowerCase().contains(finalQuery);
				}

				return nameMatch || contentMatch;
			}).collect(Collectors.toList());
		}

		return convertToResponseList(documents);
	}

	private List<MyConsentResponse> convertToResponseList(List<Document> documents) {
		List<MyConsentResponse> responseList = new ArrayList<>();
		for (Document doc : documents) {
			int totalreviewers = doc.getReviewers().size();
			int reviewrCount = (int) doc.getReviewers().stream().filter(Reviewer::getApproved).count();
			String reviewerStatus;
			if (totalreviewers == 0) {
				reviewerStatus = "No Reviewers";
			} else if (reviewrCount == totalreviewers) {
				reviewerStatus = "Approved";
			} else {
				reviewerStatus = "Pending";
			}
			String documentStatus;
			if (doc.getDocumentStatus() != null && doc.getDocumentStatus().equalsIgnoreCase("SENT_TO_SIGNERS")) {
				documentStatus = "SENT_TO_SIGNERS";
			} else if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				documentStatus = "REVIEW_COMPLETED";
			} else {
				documentStatus = "IN_REVIEW";
			}
			LocalDate reviewedOn = null;

			if (totalreviewers > 0 && reviewrCount == totalreviewers) {
				reviewedOn = doc.getReviewers().stream().map(Reviewer::getApprovedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}
			int totalSigners = doc.getSigners().size();
			int signedCount = (int) doc.getSigners().stream()
					.filter(s -> "completed".equalsIgnoreCase(s.getSignStatus())).count();

			LocalDate signedOn = null;
			if (totalSigners > 0 && signedCount == totalSigners) {
				signedOn = doc.getSigners().stream().map(Signer::getSignedAt).filter(Objects::nonNull)
						.max(LocalDate::compareTo).orElse(null);
			}

			responseList.add(new MyConsentResponse(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(),
					doc.getDraft(), signedOn, totalSigners, signedCount, doc.getEditedFile(), reviewedOn,
					totalreviewers, reviewrCount, reviewerStatus, documentStatus));
		}

		return responseList;
	}

	@Override
	public ResponseEntity<byte[]> getDocumentForReviewer(Long documentId, String email) {
		// TODO Auto-generated method stub
		Reviewer reviewer = reviewerRepo.findByReviewerEmailAndDocumentId(email, documentId);

		if (reviewer == null) {
			throw new RuntimeException("Reviewer not found for this document and email.");
		}

		Document document = reviewer.getDocument();
		byte[] fileBytes = document.getEditedFile();
		String filename = document.getDocumentName() + ".pdf";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDisposition(ContentDisposition.inline().filename(filename).build());

		return ResponseEntity.ok().headers(headers).body(fileBytes);
	}

	@Override
	public Map<String, List<ParticipantStatusDTO>> getParticipants(Long documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new NoSuchElementException("Document not found with ID: " + documentId));

		Map<String, List<ParticipantStatusDTO>> participants = new HashMap<>();

		// Sender (status: "sender")
		participants.put("sender",
				Collections.singletonList(new ParticipantStatusDTO(document.getSenderEmail(), "sender")));

		// Reviewers (status: approved or pending)
		List<ParticipantStatusDTO> reviewers = document.getReviewers().stream().filter(Objects::nonNull)
				.map(r -> new ParticipantStatusDTO(r.getReviewerEmail(),
						Boolean.TRUE.equals(r.getApproved()) ? "approved" : "pending"))
				.collect(Collectors.toList());
		participants.put("reviewers", reviewers);

		// Signers (status: completed or pending; case-insensitive check)
		List<ParticipantStatusDTO> signers = document.getSigners().stream().filter(Objects::nonNull).map(s -> {
			String signStatus = s.getSignStatus();
			boolean completed = signStatus != null && signStatus.toLowerCase().contains("completed");
			return new ParticipantStatusDTO(s.getEmail(), completed ? "completed" : "pending");
		}).collect(Collectors.toList());
		participants.put("signers", signers);

		return participants;
	}

	@Override
	public String sendToSigners(long documentId) {
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentId));

		// Only allow sending if all reviewers have approved
		if (!"REVIEW_COMPLETED".equals(document.getDocumentStatus())) {
			throw new IllegalStateException("Document is not approved by all reviewers yet.");
		}

		// Send emails to signers
		sendEmailToSigners(document);

		// Update document status
		document.setDocumentStatus("SENT_TO_SIGNERS");
		documentRepository.save(document);

		return "Document successfully sent to signers.";
	}

	@Override
	public List<UnseenDocumentDTO> getUnseenRequests(String senderEmail) {
		// Get only unseen documents that belong to this sender
		List<Document> documents = documentRepository.findBySeenBySenderFalseAndSenderEmail(senderEmail);

		List<UnseenDocumentDTO> unseenList = new ArrayList<>();

		for (Document doc : documents) {
			List<Reviewer> approvedReviewers = doc.getReviewers().stream().filter(Reviewer::getApproved)
					.collect(Collectors.toList());

			if (!approvedReviewers.isEmpty()) {
				Reviewer lastReviewer = approvedReviewers.get(approvedReviewers.size() - 1);

				unseenList.add(new UnseenDocumentDTO(doc.getId(), doc.getDocumentName(), doc.getSenderEmail(),
						lastReviewer.getReviewerEmail()));
			}
		}

		return unseenList;
	}

	@Override
	public void markAsSeen(Long documentId) {
		// TODO Auto-generated method stub
		Document document = documentRepository.findById(documentId)
				.orElseThrow(() -> new RuntimeException("Document not found"));

		document.setSeenBySender(true);
		documentRepository.save(document);
	}

}