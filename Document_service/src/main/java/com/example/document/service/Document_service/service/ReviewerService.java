package com.example.document.service.Document_service.service;

import java.util.List;

import com.example.document.service.Document_service.dto.ReviewedConsentsDTO;

public interface ReviewerService {

	List<ReviewedConsentsDTO> getUnreviewedDocuments(String reviewerEmail);

	List<ReviewedConsentsDTO> getReviewedDocuments(String reviewerEmail);

	void updateDocumentApprovement(long documentId, String reviewerEmail);

	boolean areAllReviewersApproved(long documentId);


}
