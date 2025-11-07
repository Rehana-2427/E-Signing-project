package com.companyService.company_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companyService.company_service.dto.AcceptInvitationRequest;
import com.companyService.company_service.dto.AssignedCompanyDTO;
import com.companyService.company_service.dto.InvitationUserList;
import com.companyService.company_service.dto.InviteUserRequest;
import com.companyService.company_service.dto.RecivedInvitationList;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.entity.Company_Users;
import com.companyService.company_service.service.CompanyUserService;

@RestController
@RequestMapping("/api/companyUsers")
public class CompanyUserController {

	@Autowired
	private CompanyUserService companyUserService;

	@PostMapping("/inviteUser")
	public Company_Users inviteUser(@RequestBody InviteUserRequest invitedUserRequest) {
		return companyUserService.inviteUserToCompany(invitedUserRequest.getCompanyId(),
				invitedUserRequest.getUserEmail(), invitedUserRequest.getInvitedByEmail(),
				invitedUserRequest.getMobileNumber(), invitedUserRequest.getRole());
	}

	@PutMapping("/acceptInvitation")
	public ResponseEntity<String> acceptInvitation(@RequestBody AcceptInvitationRequest request) {
		companyUserService.acceptInvitation(request);
		return ResponseEntity.ok("Invitation accepted successfully.");
	}

	@GetMapping("/getListofSentInvitations")
	public ResponseEntity<List<InvitationUserList>> listOfSentInvitations(@RequestParam String invitedByEmail) {
		List<InvitationUserList> invitationList = companyUserService.listOfSentInvitations(invitedByEmail);
		return ResponseEntity.ok(invitationList);
	}

	@GetMapping("/getListofRecievedInvitations")
	public ResponseEntity<List<RecivedInvitationList>> listOfRecievedInvitations(@RequestParam String userEmail) {
		List<RecivedInvitationList> invitationList = companyUserService.listOfRecievedInvitations(userEmail);
		return ResponseEntity.ok(invitationList);
	}

	@GetMapping("/getAssignedCompanies")
	public ResponseEntity<List<AssignedCompanyDTO>> getAssignedCompanies(@RequestParam String userEmail) {
		List<AssignedCompanyDTO> assignedCompanies = companyUserService.getAssignedCompanies(userEmail);
		return ResponseEntity.ok(assignedCompanies);
	}

	@GetMapping("/getUserByCompanyName")
	public List<Company_Users> getAcceptedUsersByCompany(@RequestParam String companyName) {
		return companyUserService.getAcceptedUsersByCompany(companyName);
	}

}
