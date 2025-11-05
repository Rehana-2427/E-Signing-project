package com.example.adminservice.adminservice.dto;

public class CreditRequestMessage {
	private String userName;
	private String userEmail;
	private String companyName;
	private String mobileNumber;
	private int requestedCredits;
	private int requestCPUnit;

	public CreditRequestMessage(String userName, String userEmail, int requestedCredits) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.requestedCredits = requestedCredits;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public int getRequestCPUnit() {
		return requestCPUnit;
	}

	public void setRequestCPUnit(int requestCPUnit) {
		this.requestCPUnit = requestCPUnit;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public int getRequestedCredits() {
		return requestedCredits;
	}

	public void setRequestedCredits(int requestedCredits) {
		this.requestedCredits = requestedCredits;
	}

}
