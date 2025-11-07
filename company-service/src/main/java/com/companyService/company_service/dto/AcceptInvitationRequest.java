package com.companyService.company_service.dto;

import com.companyService.company_service.companyuserEnum.InvitationStatus;

public class AcceptInvitationRequest {

	private String userEmail;
	private String invitedByEmail;
	private String companyName;
	private InvitationStatus invitationStatus;

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getInvitedByEmail() {
		return invitedByEmail;
	}

	public void setInvitedByEmail(String invitedByEmail) {
		this.invitedByEmail = invitedByEmail;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public InvitationStatus getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(InvitationStatus invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

}
