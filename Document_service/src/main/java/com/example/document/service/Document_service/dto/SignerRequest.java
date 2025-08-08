package com.example.document.service.Document_service.dto;

import java.util.Arrays;

import jakarta.persistence.Lob;

public class SignerRequest {
	private String name;
	private String email;
	private Long documentId;
	private String signStatus;
	private String signed_file;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getSigned_file() {
		return signed_file;
	}

	public void setSigned_file(String signed_file) {
		this.signed_file = signed_file;
	}

	@Override
	public String toString() {
		return "SignerRequest [name=" + name + ", email=" + email + ", documentId=" + documentId + ", signStatus="
				+ signStatus + ", signed_file=" + signed_file + "]";
	}

}
