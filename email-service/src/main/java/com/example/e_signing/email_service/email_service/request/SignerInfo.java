package com.example.e_signing.email_service.email_service.request;

import java.time.LocalDate;

public class SignerInfo {
	private String name;
	private String email;
	private LocalDate signedAt;

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

}
