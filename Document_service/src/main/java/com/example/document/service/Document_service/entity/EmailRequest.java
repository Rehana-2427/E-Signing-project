package com.example.document.service.Document_service.entity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmailRequest {

	private String senderEmail;
	private List<String> recipientEmails;
	private String title;
	private LocalDate signRequiredBy;
	private byte[] pdfBytes;

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public List<String> getRecipientEmails() {
		return recipientEmails;
	}

	public void setRecipientEmails(List<String> recipientEmails) {
		this.recipientEmails = recipientEmails;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public LocalDate getSignRequiredBy() {
		return signRequiredBy;
	}

	public void setSignRequiredBy(LocalDate signRequiredBy) {
		this.signRequiredBy = signRequiredBy;
	}

	public byte[] getPdfBytes() {
		return pdfBytes;
	}

	public void setPdfBytes(byte[] pdfBytes) {
		this.pdfBytes = pdfBytes;
	}

	@Override
	public String toString() {
		return "EmailRequest [senderEmail=" + senderEmail + ", recipientEmails=" + recipientEmails + ", title=" + title
				+ ", signRequiredBy=" + signRequiredBy + ", pdfBytes=" + Arrays.toString(pdfBytes) + "]";
	}

}
