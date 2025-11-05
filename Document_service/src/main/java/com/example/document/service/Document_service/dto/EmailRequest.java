package com.example.document.service.Document_service.dto;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class EmailRequest {

	private String senderEmail;
//	private List<String> recipientEmails;
	private String title;
	private LocalDate signRequiredBy;
	private byte[] pdfBytes;
	private String senderName;
	private String companyName;
	private String to;
	private LocalDate signedAt;
	private String summaryStatus;
	private Long Id;

	private List<RecipientToken> recipients;

	private List<SignerInfo> signers;

	private List<ReviewrRequest> reviewerEmails;

	private List<ReviewerToken> reviewers;

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

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public List<RecipientToken> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<RecipientToken> recipients) {
		this.recipients = recipients;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
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
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public List<SignerInfo> getSigners() {
		return signers;
	}

	public void setSigners(List<SignerInfo> signers) {
		this.signers = signers;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public List<ReviewrRequest> getReviewerEmails() {
		return reviewerEmails;
	}

	public void setReviewerEmails(List<ReviewrRequest> reviewerEmails) {
		this.reviewerEmails = reviewerEmails;
	}

	public List<ReviewerToken> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<ReviewerToken> reviewers) {
		this.reviewers = reviewers;
	}

	@Override
	public String toString() {
		return "EmailRequest [senderEmail=" + senderEmail + ", title=" + title + ", signRequiredBy=" + signRequiredBy
				+ ", pdfBytes=" + Arrays.toString(pdfBytes) + ", senderName=" + senderName + ", recipients="
				+ recipients + "]";
	}

}
