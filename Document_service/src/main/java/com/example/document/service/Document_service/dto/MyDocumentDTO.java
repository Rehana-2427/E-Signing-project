package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class MyDocumentDTO {

	private Long documentId;
	private String documentName;
	private LocalDate createdDate;
	private LocalDate signedAt;
	private String signStatus;

	public MyDocumentDTO(Long documentId, String documentName, LocalDate createdDate, LocalDate signedAt,
			String signStatus) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.createdDate = createdDate;
		this.signedAt = signedAt;
		this.signStatus = signStatus;
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

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getSignedAt() {
		return signedAt;
	}

	public void setSignedAt(LocalDate signedAt) {
		this.signedAt = signedAt;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

}
