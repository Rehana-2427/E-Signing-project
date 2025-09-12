package com.example.adminservice.adminservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adminservice.adminservice.dto.EmailCreditResponse;
import com.example.adminservice.adminservice.dto.UserDTO;
import com.example.adminservice.adminservice.fiegnclient.EmailCreditsClient;
import com.example.adminservice.adminservice.modal.CreditTransaction;
import com.example.adminservice.adminservice.modal.UserCredits;
import com.example.adminservice.adminservice.repo.CreditTransactionRepository;
import com.example.adminservice.adminservice.repo.UserCreditsRepository;
import com.example.adminservice.adminservice.service.UserCreditsService;

@Service
public class UserCreditsServiceImpl implements UserCreditsService {

	@Autowired
	private UserCreditsRepository userCreditsRepository;

	@Autowired
	private CreditTransactionRepository creditTransactionRepository;
	
	@Autowired
	private EmailCreditsClient emailCreditsClient;

	@Override
	public String createUserCredits(UserDTO userCreditsDTO) {
		UserCredits existing = userCreditsRepository.findByUserEmail(userCreditsDTO.getUserEmail());
		if (existing == null) {
			UserCredits newUser = new UserCredits();
			newUser.setUserEmail(userCreditsDTO.getUserEmail());
			newUser.setUserName(userCreditsDTO.getUserName());
			newUser.setCreditBought(0);
			newUser.setUsedCredit(0);
			newUser.setBalanceCredit(0);
			newUser.setUpdatedAt(LocalDate.now());
			userCreditsRepository.save(newUser);
			return "User credits created";
		} else {
			return "User already exists in credits table";
		}
	}

	@Override
	public void assignCreditsToUser(String userEmail, int creditsToAssign) {
		UserCredits user = userCreditsRepository.findByUserEmail(userEmail);
		if (user == null) {
			throw new RuntimeException("User not found with email: " + userEmail);
		}

		int newCredits = user.getCreditBought() + creditsToAssign;
		int balance = newCredits - user.getUsedCredit();

		user.setCreditBought(newCredits);
		user.setBalanceCredit(balance);
		user.setUpdatedAt(LocalDate.now());

		userCreditsRepository.save(user);

		try {
			EmailCreditResponse email = new EmailCreditResponse();
			email.setTo(user.getUserEmail());
			email.setSenderEmail("admin@yourcompany.com");
			email.setUserName(user.getUserName());
			email.setCreditsAssigned(creditsToAssign);
			email.setBalance(balance);

			emailCreditsClient.sendCreditsEmail(email);

		} catch (Exception e) {
			System.err.println("Failed to send email: " + e.getMessage());
			// Log it if needed
		}
	}

	@Override
	public UserCredits updateUserCredits(String userEmail, int balanceCredit, int usedCredit) {
		// TODO Auto-generated method stub
		UserCredits usercredits = userCreditsRepository.findByUserEmail(userEmail);
		usercredits.setBalanceCredit(balanceCredit);
		usercredits.setUsedCredit(usedCredit);
		usercredits.setUpdatedAt(LocalDate.now());
		return userCreditsRepository.save(usercredits);
	}

	@Override
	public CreditTransaction saveCreditTransaction(CreditTransaction transaction) {
		// TODO Auto-generated method stub
		transaction.setDate(LocalDate.now());
		return creditTransactionRepository.save(transaction);
	}

	@Override
	public List<CreditTransaction> getCreditTransactionsByUser(String userEmail) {
		return creditTransactionRepository.findByUserEmail(userEmail);
	}

}
