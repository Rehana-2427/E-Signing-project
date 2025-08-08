package com.example.e_signing.email_service.email_service.request;

import java.util.List;

public class ReminderRequest {
	private String from;
	private String replyTo;
	private List<String> to;
	private String subject;
	private String documentName;

	public ReminderRequest(String from, String replyTo, List<String> to, String subject, String documentName) {
		super();
		this.from = from;
		this.replyTo = replyTo;
		this.to = to;
		this.subject = subject;
		this.documentName = documentName;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}
