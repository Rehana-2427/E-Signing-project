package com.companyService.company_service.dto;

import java.time.LocalDate;

import com.companyService.company_service.companyuserEnum.InvitationStatus;
import com.companyService.company_service.companyuserEnum.Role;

public class InvitationUserList {

	private String userEmail;
	private String mobileNumber;
	private String companyName;
	private Role role;
	private InvitationStatus invitationStatus;
	private LocalDate acceptedAt;

	public InvitationUserList(String userEmail, String mobileNumber, String companyName, Role role,
			InvitationStatus invitationStatus, LocalDate acceptedAt) {
		super();
		this.userEmail = userEmail;
		this.mobileNumber = mobileNumber;
		this.companyName = companyName;
		this.role = role;
		this.invitationStatus = invitationStatus;
		this.acceptedAt = acceptedAt;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public InvitationStatus getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(InvitationStatus invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

	public LocalDate getAcceptedAt() {
		return acceptedAt;
	}

	public void setAcceptedAt(LocalDate acceptedAt) {
		this.acceptedAt = acceptedAt;
	}

}
