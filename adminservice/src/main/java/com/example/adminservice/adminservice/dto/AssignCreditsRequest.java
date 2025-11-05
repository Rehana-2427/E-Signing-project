package com.example.adminservice.adminservice.dto;

public class AssignCreditsRequest {
	private String userEmail;
	private int creditsToAssign;
	private String companyName;
	private int assignCPU;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getCreditsToAssign() {
		return creditsToAssign;
	}

	public void setCreditsToAssign(int creditsToAssign) {
		this.creditsToAssign = creditsToAssign;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getAssignCPU() {
		return assignCPU;
	}

	public void setAssignCPU(int assignCPU) {
		this.assignCPU = assignCPU;
	}

}
