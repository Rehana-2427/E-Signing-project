package com.example.document.service.Document_service.dto;

import java.time.LocalDate;
import java.util.List;

public class DocumentRequest {
	private String senderEmail;
	private String senderName;
	private String documentName;
	private String description;
	private String fileName;
	private String signingMode;
	private String additionalInitials;
	private LocalDate deadline;
	private Boolean reminderEveryDay;
	private Boolean reminderDaysBeforeEnabled;
	private Integer reminderDaysBefore;
	private Boolean reminderLastDay;
	private Boolean sendFinalCopy;
	private Boolean draft = false;
	private Integer documentCharge;
	private Integer signatoryCharge;
	private Integer totalCredits;

	private List<SignerRequest> signers;

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	
	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getSigningMode() {
		return signingMode;
	}

	public void setSigningMode(String signingMode) {
		this.signingMode = signingMode;
	}

	public String getAdditionalInitials() {
		return additionalInitials;
	}

	public void setAdditionalInitials(String additionalInitials) {
		this.additionalInitials = additionalInitials;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public Boolean getReminderEveryDay() {
		return reminderEveryDay;
	}

	public void setReminderEveryDay(Boolean reminderEveryDay) {
		this.reminderEveryDay = reminderEveryDay;
	}

	public Boolean getReminderDaysBeforeEnabled() {
		return reminderDaysBeforeEnabled;
	}

	public void setReminderDaysBeforeEnabled(Boolean reminderDaysBeforeEnabled) {
		this.reminderDaysBeforeEnabled = reminderDaysBeforeEnabled;
	}

	public Integer getReminderDaysBefore() {
		return reminderDaysBefore;
	}

	public void setReminderDaysBefore(Integer reminderDaysBefore) {
		this.reminderDaysBefore = reminderDaysBefore;
	}

	public Boolean getReminderLastDay() {
		return reminderLastDay;
	}

	public void setReminderLastDay(Boolean reminderLastDay) {
		this.reminderLastDay = reminderLastDay;
	}

	public Boolean getSendFinalCopy() {
		return sendFinalCopy;
	}

	public void setSendFinalCopy(Boolean sendFinalCopy) {
		this.sendFinalCopy = sendFinalCopy;
	}

	public Integer getDocumentCharge() {
		return documentCharge;
	}

	public void setDocumentCharge(Integer documentCharge) {
		this.documentCharge = documentCharge;
	}

	public Integer getSignatoryCharge() {
		return signatoryCharge;
	}

	public void setSignatoryCharge(Integer signatoryCharge) {
		this.signatoryCharge = signatoryCharge;
	}

	public Integer getTotalCredits() {
		return totalCredits;
	}

	public void setTotalCredits(Integer totalCredits) {
		this.totalCredits = totalCredits;
	}

	public List<SignerRequest> getSigners() {
		return signers;
	}

	public void setSigners(List<SignerRequest> signers) {
		this.signers = signers;
	}

	public Boolean getDraft() {
		return draft;
	}

	public void setDraft(Boolean draft) {
		this.draft = draft;
	}

	@Override
	public String toString() {
		return "DocumentRequest [documentName=" + documentName + ", description=" + description + ", fileName="
				+ fileName + ", signingMode=" + signingMode + ", additionalInitials=" + additionalInitials
				+ ", deadline=" + deadline + ", reminderEveryDay=" + reminderEveryDay + ", reminderDaysBeforeEnabled="
				+ reminderDaysBeforeEnabled + ", reminderDaysBefore=" + reminderDaysBefore + ", reminderLastDay="
				+ reminderLastDay + ", sendFinalCopy=" + sendFinalCopy + ", documentCharge=" + documentCharge
				+ ", signatoryCharge=" + signatoryCharge + ", totalCredits=" + totalCredits + ", signers=" + signers
				+ "]";
	}

}
