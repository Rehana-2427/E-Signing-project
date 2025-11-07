package com.companyService.company_service.service;

import java.util.List;

import com.companyService.company_service.companyuserEnum.Role;
import com.companyService.company_service.dto.AcceptInvitationRequest;
import com.companyService.company_service.dto.AssignedCompanyDTO;
import com.companyService.company_service.dto.InvitationUserList;
import com.companyService.company_service.dto.RecivedInvitationList;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.entity.Company_Users;

public interface CompanyUserService {

	Company_Users inviteUserToCompany(Long companyId, String userEmail, String invitedByEmail, String mobileNumber, Role role);

	void acceptInvitation(AcceptInvitationRequest request);

	List<InvitationUserList> listOfSentInvitations(String invitedByEmail);

	List<RecivedInvitationList> listOfRecievedInvitations(String userEmail);

	List<AssignedCompanyDTO> getAssignedCompanies(String userEmail);

	List<Company_Users> getAcceptedUsersByCompany(String companyName);
	
	

}
