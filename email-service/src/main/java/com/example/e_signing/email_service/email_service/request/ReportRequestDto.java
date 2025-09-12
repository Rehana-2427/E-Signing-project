package com.example.e_signing.email_service.email_service.request;

import java.time.LocalDate;
import java.util.List;

public class ReportRequestDto {

	private String documentName;
	private String senderEmail;
	private List<SignerInfo> signerList;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public List<SignerInfo> getSignerList() {
		return signerList;
	}

	public void setSignerList(List<SignerInfo> signerList) {
		this.signerList = signerList;
	}

	public static class SignerInfo {
		private String name;
		private String email;
		private LocalDate signedAt;

		// Getters and Setters
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

}
