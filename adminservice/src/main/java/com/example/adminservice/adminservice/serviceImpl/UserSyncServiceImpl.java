package com.example.adminservice.adminservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adminservice.adminservice.dto.UserCreditsDTO;
import com.example.adminservice.adminservice.dto.UserDTO;
import com.example.adminservice.adminservice.fiegnclient.UserClient;
import com.example.adminservice.adminservice.modal.UserCredits;
import com.example.adminservice.adminservice.repo.UserCreditsRepository;
import com.example.adminservice.adminservice.service.UserSyncService;

@Service
public class UserSyncServiceImpl implements UserSyncService {

	@Autowired
	private UserCreditsRepository userCreditsRepository;

	@Autowired
	private UserClient userClient;

	@Override
	public void syncUsers() {
		List<UserDTO> users = userClient.getAllUsers();

		for (UserDTO user : users) {
			UserCredits existing = userCreditsRepository.findByUserEmail(user.getUserEmail());
			if (existing == null) {
				UserCredits newUser = new UserCredits();
				newUser.setUserEmail(user.getUserEmail());
				newUser.setUserName(user.getUserName());
				newUser.setCreditBought(0);
				newUser.setUsedCredit(0);
				newUser.setBalanceCredit(0);
				newUser.setUpdatedAt(LocalDate.now());
				userCreditsRepository.save(newUser);
			}
		}
	}

	@Override
	public List<UserCredits> getAllUserCredits() {
		return userCreditsRepository.findAll();
	}

	@Override
	public UserCreditsDTO getUserCreditsByEmail(String userEmail) {
		// TODO Auto-generated method stub
		UserCredits userCredits = userCreditsRepository.findByUserEmail(userEmail);

		if (userCredits == null) {
			throw new RuntimeException("User not found with email: " + userEmail);
		}

		return new UserCreditsDTO(userCredits.getCreditBought(), userCredits.getUsedCredit(),
				userCredits.getBalanceCredit());

	}


}
