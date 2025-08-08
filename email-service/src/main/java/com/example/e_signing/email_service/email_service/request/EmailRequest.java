package com.example.e_signing.email_service.email_service.request;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmailRequest {

	private String to;
	private String subject;
	private String body;
	private String otp;
	private String senderName;
	private String senderEmail;
//	private List<String> recipientEmails;
	private List<RecipientToken> recipients;
	private String title;
	private LocalDate signRequiredBy;
	private byte[] pdfBytes;
	private LocalDate signedAt;
	private String summaryStatus;
	private Long id;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

//	public List<String> getRecipientEmails() {
//		return recipientEmails;
//	}
//
//	public void setRecipientEmails(List<String> recipientEmails) {
//		this.recipientEmails = recipientEmails;
//	}

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

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public List<RecipientToken> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<RecipientToken> recipients) {
		this.recipients = recipients;
	}

	public LocalDate getSignedAt() {
		return signedAt;
	}

	public void setSignedAt(LocalDate signedAt) {
		this.signedAt = signedAt;
	}

	public String getSummaryStatus() {
		return summaryStatus;
	}

	public void setSummaryStatus(String summaryStatus) {
		this.summaryStatus = summaryStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EmailRequest [to=" + to + ", subject=" + subject + ", body=" + body + ", otp=" + otp + ", senderName="
				+ senderName + ", senderEmail=" + senderEmail + ", recipients=" + recipients + ", title=" + title
				+ ", signRequiredBy=" + signRequiredBy + ", pdfBytes=" + Arrays.toString(pdfBytes) + "]";
	}

}
