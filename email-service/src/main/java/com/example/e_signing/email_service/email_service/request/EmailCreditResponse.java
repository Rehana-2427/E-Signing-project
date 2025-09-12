package com.example.e_signing.email_service.email_service.request;

public class EmailCreditResponse {
	private String to;
	private String senderEmail;
	private String userName;
	private int creditsAssigned;
	private int balance;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getCreditsAssigned() {
		return creditsAssigned;
	}

	public void setCreditsAssigned(int creditsAssigned) {
		this.creditsAssigned = creditsAssigned;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

}
