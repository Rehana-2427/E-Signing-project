package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class DocumentStatusDTO {

	private Long documentId;
	private String title;
	private LocalDate sentOn;
	private LocalDate latestSignedDate;
	private int totalSigners;
	private int signedCount;

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getSentOn() {
		return sentOn;
	}

	public void setSentOn(LocalDate sentOn) {
		this.sentOn = sentOn;
	}

	public LocalDate getLatestSignedDate() {
		return latestSignedDate;
	}

	public void setLatestSignedDate(LocalDate latestSignedDate) {
		this.latestSignedDate = latestSignedDate;
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

	@Override
	public String toString() {
		return "DocumentStatusDTO [documentId=" + documentId + ", title=" + title + ", sentOn=" + sentOn
				+ ", latestSignedDate=" + latestSignedDate + ", totalSigners=" + totalSigners + ", signedCount="
				+ signedCount + "]";
	}

}
