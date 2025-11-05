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

import com.example.document.service.Document_service.dto.ReviewedConsentsDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.service.DocumentService;
import com.example.document.service.Document_service.service.ReviewerService;

@RestController
@RequestMapping("/api/reviewer")
public class ReviewerController {

	@Autowired
	private ReviewerService reviewerService;

	@Autowired
	private DocumentService documentService;

	@GetMapping("/unreviewedDocs")
	public List<ReviewedConsentsDTO> getUnreviewedDocuments(@RequestParam String reviewerEmail) {
		return reviewerService.getUnreviewedDocuments(reviewerEmail);
	}

	@GetMapping("/reviewedDocs")
	public List<ReviewedConsentsDTO> getReviewedDocuments(@RequestParam String reviewerEmail) {
		return reviewerService.getReviewedDocuments(reviewerEmail);
	}

	@PutMapping("/approveDocument")
	public ResponseEntity<String> updateDocumentApprovement(@RequestParam long documentId,
			@RequestParam String reviewerEmail) {
		try {
			// Update reviewer approval status
			reviewerService.updateDocumentApprovement(documentId, reviewerEmail);

			return ResponseEntity.ok("Reviewer approved successfully. Waiting for other reviewers.");

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error: " + e.getMessage());
		}
	}

}
