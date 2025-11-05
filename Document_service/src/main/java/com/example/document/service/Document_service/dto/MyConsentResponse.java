package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class MyConsentResponse {

	private Long documentId;
	private String documentName;
	private LocalDate sentOn; // corresponds to Document.createdDate
	private boolean draft;
	private LocalDate signedOn; // latest sign date if all signed
	private int totalSigners;
	private int signedCount;
	private byte[] editedFile;
	private LocalDate reviewedOn;
	private int totalReviwers;
	private int reviwercount;
	private String reviewerStatus;
	private String documentStatus;

	public MyConsentResponse(Long documentId, String documentName, LocalDate sentOn, boolean draft, LocalDate signedOn,
			int totalSigners, int signedCount, byte[] editedFile, LocalDate reviewedOn, int totalReviwers,
			int reviwercount, String reviewerStatus,String documentStatus) {
		super();
		this.documentId = documentId;
		this.documentName = documentName;
		this.sentOn = sentOn;
		this.draft = draft;
		this.signedOn = signedOn;
		this.totalSigners = totalSigners;
		this.signedCount = signedCount;
		this.editedFile = editedFile;
		this.reviewedOn = reviewedOn;
		this.totalReviwers = totalReviwers;
		this.reviwercount = reviwercount;
		this.reviewerStatus = reviewerStatus;
		this.documentStatus=documentStatus;
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

	public LocalDate getSentOn() {
		return sentOn;
	}

	public void setSentOn(LocalDate sentOn) {
		this.sentOn = sentOn;
	}

	public boolean isDraft() {
		return draft;
	}

	public void setDraft(boolean draft) {
		this.draft = draft;
	}

	public LocalDate getSignedOn() {
		return signedOn;
	}

	public void setSignedOn(LocalDate signedOn) {
		this.signedOn = signedOn;
	}

	public int getTotalSigners() {
		return totalSigners;
	}

	public void setTotalSigners(int totalSigners) {
		this.totalSigners = totalSigners;
	}

	public int getSignedCount() {
		return signedCount;
	}

	public void setSignedCount(int signedCount) {
		this.signedCount = signedCount;
	}

	public byte[] getEditedFile() {
		return editedFile;
	}

	public void setEditedFile(byte[] editedFile) {
		this.editedFile = editedFile;
	}

	public LocalDate getReviewedOn() {
		return reviewedOn;
	}

	public void setReviewedOn(LocalDate reviewedOn) {
		this.reviewedOn = reviewedOn;
	}

	public int getTotalReviwers() {
		return totalReviwers;
	}

	public void setTotalReviwers(int totalReviwers) {
		this.totalReviwers = totalReviwers;
	}

	public int getReviwercount() {
		return reviwercount;
	}

	public void setReviwercount(int reviwercount) {
		this.reviwercount = reviwercount;
	}

	public String getReviewerStatus() {
		return reviewerStatus;
	}

	public void setReviewerStatus(String reviewerStatus) {
		this.reviewerStatus = reviewerStatus;
	}

	public String getDocumentStatus() {
		return documentStatus;
	}

	public void setDocumentStatus(String documentStatus) {
		this.documentStatus = documentStatus;
	}

	@Override
	public String toString() {
		return "MyConsentResponse [documentId=" + documentId + ", documentName=" + documentName + ", sentOn=" + sentOn
				+ ", draft=" + draft + ", signedOn=" + signedOn + ", totalSigners=" + totalSigners + ", signedCount="
				+ signedCount + "]";
	}
}
