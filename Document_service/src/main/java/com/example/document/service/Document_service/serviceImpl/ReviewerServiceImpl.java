package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.RecipientToken;
import com.example.document.service.Document_service.dto.ReviewedConsentsDTO;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Reviewer;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.jwttoken.JwtUtil;
import com.example.document.service.Document_service.repo.DocumentRepository;
import com.example.document.service.Document_service.repo.ReviewerRepo;
import com.example.document.service.Document_service.service.ReviewerService;

@Service
public class ReviewerServiceImpl implements ReviewerService {

	@Autowired
	private ReviewerRepo reviewerRepo;

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private EmailClient emailClient;

	@Override
	public List<ReviewedConsentsDTO> getUnreviewedDocuments(String reviewerEmail) {
		// TODO Auto-generated method stub
		return reviewerRepo.findUnreviewedDocumentsByReviewerEmail(reviewerEmail);
	}

	@Override
	public List<ReviewedConsentsDTO> getReviewedDocuments(String reviewerEmail) {
		// TODO Auto-generated method stub
		return reviewerRepo.findReviewedDocumentsByReviewerEmail(reviewerEmail);
	}

	@Override
	public void updateDocumentApprovement(long documentId, String reviewerEmail) {
		Reviewer reviewer = reviewerRepo.findByReviewerEmailAndDocumentId(reviewerEmail, documentId);

		if (reviewer != null) {
			reviewer.setApproved(true);
			reviewer.setApprovedAt(LocalDate.now()); // Set the date when approved
			reviewerRepo.save(reviewer); // Save the updated reviewer entity

			Document document = documentRepository.findById(documentId)
					.orElseThrow(() -> new RuntimeException("Document not found with ID: " + documentId));

			document.setSeenBySender(false);
			boolean allReviewersApproved = document.getReviewers().stream().allMatch(Reviewer::getApproved);

			if (allReviewersApproved) {
//				sendEmailToSigners(document);
				document.setDocumentStatus("REVIEW_COMPLETED");
				documentRepository.save(document);
			}
		} else {
			throw new RuntimeException("Reviewer not found for this document");
		}
	}

	private void sendEmailToSigners(Document document) {
		// Create recipient tokens for signers
		List<RecipientToken> recipientTokens = new ArrayList<>();
		for (Signer signer : document.getSigners()) {
			String jwtToken = JwtUtil.generateToken(document.getSenderName(), document.getDocumentName(),
					signer.getEmail(), document.getId(), java.sql.Date.valueOf(document.getDeadline()));

			recipientTokens.add(new RecipientToken(signer.getName(), signer.getEmail(), document.getId(), jwtToken));
		}

		// Prepare and send the email request to signers
//		EmailRequest emailRequest = new EmailRequest();
//		emailRequest.setSenderEmail(document.getSenderEmail());
//		emailRequest.setRecipients(recipientTokens);
//		emailRequest.setTitle("Please sign the document: " + document.getDocumentName());
//		emailRequest.setSignRequiredBy(document.getDeadline());
//		emailRequest.setSenderName(document.getSenderName());
//
//		emailClient.sendDocumentSigningRequestEmail(emailRequest);
	}

	@Override
	public boolean areAllReviewersApproved(long documentId) {
		// TODO Auto-generated method stub
		List<Reviewer> reviewers = reviewerRepo.findByDocumentId(documentId);
		return reviewers.stream().allMatch(Reviewer::getApproved);
	}

}
