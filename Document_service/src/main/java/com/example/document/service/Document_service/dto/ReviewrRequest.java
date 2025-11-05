package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class ReviewrRequest {
	private String reviewerEmail;
	private Boolean approved = false;
	private LocalDate approvedAt;
	private Long documentId;

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public LocalDate getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(LocalDate approvedAt) {
		this.approvedAt = approvedAt;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

}
