package com.companyService.company_service.dto;

import java.time.LocalDate;

import com.companyService.company_service.companyuserEnum.InvitationStatus;
import com.companyService.company_service.companyuserEnum.Role;

public class RecivedInvitationList {
	private String invitedByEmail;
	private String companyName;
	private String mobileNumber;
	private Role role;
	private LocalDate createdAt;
	private InvitationStatus invitationStatus;

	public RecivedInvitationList(String invitedByEmail, String companyName, String mobileNumber, Role role,
			LocalDate createdAt, InvitationStatus invitationStatus) {
		super();
		this.invitedByEmail = invitedByEmail;
		this.companyName = companyName;
		this.mobileNumber = mobileNumber;
		this.role = role;
		this.createdAt = createdAt;
		this.invitationStatus = invitationStatus;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

	public InvitationStatus getInvitationStatus() {
		return invitationStatus;
	}

	public void setInvitationStatus(InvitationStatus invitationStatus) {
		this.invitationStatus = invitationStatus;
	}

}
