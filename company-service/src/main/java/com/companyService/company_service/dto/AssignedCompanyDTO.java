package com.companyService.company_service.dto;

import com.companyService.company_service.companyuserEnum.Role;

public class AssignedCompanyDTO {
	private String companyName;
	private String description;
	private String invitedByEmail;
	private Role role;

	public AssignedCompanyDTO(String companyName, String description, String invitedByEmail, Role role) {
		super();
		this.companyName = companyName;
		this.description = description;
		this.invitedByEmail = invitedByEmail;
		this.role = role;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInvitedByEmail() {
		return invitedByEmail;
	}

	public void setInvitedByEmail(String invitedByEmail) {
		this.invitedByEmail = invitedByEmail;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
