package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class ReviewedConsentsDTO {
	private long documentId;
	private String documentName;
	private String companyName;
	private String senderEmail;
	private byte[] editedFile;
	private LocalDate approvedAt;

	public ReviewedConsentsDTO(long documentId, String documentName, String companyName, String senderEmail,
			byte[] editedFile, LocalDate approvedAt) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.companyName = companyName;
		this.senderEmail = senderEmail;
		this.editedFile = editedFile;
		this.approvedAt = approvedAt;
	}

	public ReviewedConsentsDTO(long documentId, String documentName, String companyName, String senderEmail,
			byte[] editedFile) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.companyName = companyName;
		this.senderEmail = senderEmail;
		this.editedFile = editedFile;
	}

	public long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(long documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public byte[] getEditedFile() {
		return editedFile;
	}

	public void setEditedFile(byte[] editedFile) {
		this.editedFile = editedFile;
	}

	public LocalDate getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(LocalDate approvedAt) {
		this.approvedAt = approvedAt;
	}

}
