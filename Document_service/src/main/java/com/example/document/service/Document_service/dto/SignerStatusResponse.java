package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class SignerStatusResponse {

	private String name;
	private String email;
	private String signStatus;
	private LocalDate signedAt;

	public SignerStatusResponse(String name, String email, String signStatus, LocalDate signedAt) {
		super();
		this.name = name;
		this.email = email;
		this.signStatus = signStatus;
		this.signedAt = signedAt;
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

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public LocalDate getSignedAt() {
		return signedAt;
	}

	public void setSignedAt(LocalDate signedAt) {
		this.signedAt = signedAt;
	}

}
