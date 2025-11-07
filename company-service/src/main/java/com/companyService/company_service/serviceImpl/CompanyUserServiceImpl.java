package com.companyService.company_service.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companyService.company_service.companyuserEnum.InvitationStatus;
import com.companyService.company_service.companyuserEnum.Role;
import com.companyService.company_service.dto.AcceptInvitationRequest;
import com.companyService.company_service.dto.AssignedCompanyDTO;
import com.companyService.company_service.dto.InvitationUserList;
import com.companyService.company_service.dto.RecivedInvitationList;
import com.companyService.company_service.dto.SendInviteToUser;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.entity.Company_Users;
import com.companyService.company_service.feignClient.SendInviteToUserByEmail;
import com.companyService.company_service.jwttoken.JwtUtil;
import com.companyService.company_service.repository.CompanyRepo;
import com.companyService.company_service.repository.CompanyUsersRepository;
import com.companyService.company_service.service.CompanyUserService;

@Service
public class CompanyUserServiceImpl implements CompanyUserService {

	@Autowired
	private CompanyRepo companyRepo;

	@Autowired
	private CompanyUsersRepository companyUsersRepository;

	@Autowired
	private SendInviteToUserByEmail sendInviteToUserByEmail;

	@Override
	public Company_Users inviteUserToCompany(Long companyId, String userEmail, String invitedByEmail,
			String mobileNumber, Role role) {
		Company company = companyRepo.findById(companyId)
				.orElseThrow(() -> new IllegalArgumentException("Company not found"));

		companyUsersRepository.findByUserEmailAndCompanyId(userEmail, companyId).ifPresent(existing -> {
			throw new IllegalStateException("User already invited to this company.");
		});

		Company_Users user = new Company_Users();
		user.setCompany(company);
		user.setUserEmail(userEmail);
		user.setInvitedByEmail(invitedByEmail);
		user.setMobileNumber(mobileNumber);
		user.setRole(role);

		user.setInvitationStatus(InvitationStatus.PENDING);
		user.setCreatedAt(LocalDate.now());
		user.setAcceptedAt(null);

		String token = JwtUtil.generateToken(user.getCompany().getCompanyName(), user.getInvitedByEmail(),
				user.getUserEmail());

		SendInviteToUser inviteUser = new SendInviteToUser();
		inviteUser.setSenderEmail(user.getInvitedByEmail());
		inviteUser.setCompanyName(user.getCompany().getCompanyName());
		inviteUser.setTo(user.getUserEmail());
		inviteUser.setRole(role.name());
		inviteUser.setToken(token);

		sendInviteToUserByEmail.sendEmail(inviteUser);
		return companyUsersRepository.save(user);

	}

	@Override
	public void acceptInvitation(AcceptInvitationRequest request) {
		Company company = companyRepo.findByCompanyName(request.getCompanyName()).orElseThrow(
				() -> new IllegalArgumentException("Company not found with name: " + request.getCompanyName()));

		Company_Users user = companyUsersRepository.findByUserEmailAndInvitedByEmailAndCompany_Id(
				request.getUserEmail(), request.getInvitedByEmail(), company.getId());

		if (user == null) {
			throw new RuntimeException("Invitation not found with provided details.");
		}

		if (user.getInvitationStatus() == InvitationStatus.ACCEPTED) {
			throw new RuntimeException("Invitation already accepted.");
		}

		user.setInvitationStatus(InvitationStatus.ACCEPTED);
		user.setAcceptedAt(LocalDate.now());

		companyUsersRepository.save(user);
	}

	@Override
	public List<InvitationUserList> listOfSentInvitations(String invitedByEmail) {
		List<Company_Users> users = companyUsersRepository.findByInvitedByEmail(invitedByEmail);

		return users.stream()
				.map(user -> new InvitationUserList(user.getUserEmail(), user.getMobileNumber(),
						user.getCompany().getCompanyName(), user.getRole(), user.getInvitationStatus(),
						user.getAcceptedAt()))
				.collect(Collectors.toList());
	}

	@Override
	public List<RecivedInvitationList> listOfRecievedInvitations(String userEmail) {
		// TODO Auto-generated method stub
		List<Company_Users> usersList = companyUsersRepository.findByUserEmail(userEmail);

		return usersList.stream()
				.map(user -> new RecivedInvitationList(user.getInvitedByEmail(), user.getCompany().getCompanyName(),
						user.getMobileNumber(), user.getRole(), user.getCreatedAt(), user.getInvitationStatus()))
				.collect(Collectors.toList());
	}

	@Override
	public List<AssignedCompanyDTO> getAssignedCompanies(String userEmail) {
		return companyUsersRepository.findAssignedCompaniesByUserEmail(userEmail);
	}

	@Override
	public List<Company_Users> getAcceptedUsersByCompany(String companyName) {
		// TODO Auto-generated method stub
		return companyUsersRepository.findAcceptedUsersByCompanyName(companyName);
	}

}
