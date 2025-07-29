package com.example.document.service.Document_service.dto;

public class SignerRequest {
	private String name;
	private String email;
    private Long documentId;
    private String signStatus;
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

	@Override
	public String toString() {
		return "SignerRequest [name=" + name + ", email=" + email + "]";
	}

}
