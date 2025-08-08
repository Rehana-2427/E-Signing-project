package com.example.document.service.Document_service.dto;

public class SignersContact {

	private String name;
	private String email;
	private Long signedCount;

	public SignersContact(String name, String email, Long signedCount) {
		super();
		this.name = name;
		this.email = email;
		this.signedCount = signedCount;
	}

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

	public Long getSignedCount() {
		return signedCount;
	}

	public void setSignedCount(Long signedCount) {
		this.signedCount = signedCount;
	}

}
