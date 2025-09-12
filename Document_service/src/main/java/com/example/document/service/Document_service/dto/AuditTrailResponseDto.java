package com.example.document.service.Document_service.dto;

import java.util.List;

public class AuditTrailResponseDto {
	private String documentName;
	private List<SignerInfo> signers;
	private boolean allCompleted;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public List<SignerInfo> getSigners() {
		return signers;
	}

	public void setSigners(List<SignerInfo> signers) {
		this.signers = signers;
	}

	public boolean isAllCompleted() {
		return allCompleted;
	}

	public void setAllCompleted(boolean allCompleted) {
		this.allCompleted = allCompleted;
	}

}
