package com.example.document.service.Document_service.dto;

public class UnseenDocumentDTO {
	private Long documentId;
	private String documentName;
	private String senderEmail;
	private String reviewerEmail;

	public UnseenDocumentDTO(Long documentId, String documentName, String senderEmail, String reviewerEmail) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.senderEmail = senderEmail;
		this.reviewerEmail = reviewerEmail;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

}
