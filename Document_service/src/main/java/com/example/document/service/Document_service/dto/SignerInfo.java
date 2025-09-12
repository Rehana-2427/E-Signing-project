package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class SignerInfo {
	private String name;
	private String email;
	private LocalDate signedAt;
	private String signStatus;

	public SignerInfo(String name, String email, LocalDate signedAt, String signStatus) {
		super();
		this.name = name;
		this.email = email;
		this.signedAt = signedAt;
		this.signStatus = signStatus;
	}

	public SignerInfo() {
		// TODO Auto-generated constructor stub
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
