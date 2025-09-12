package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.dto.UserDTO;
import com.example.adminservice.adminservice.modal.CreditTransaction;
import com.example.adminservice.adminservice.modal.UserCredits;

public interface UserCreditsService {

	void assignCreditsToUser(String email, int creditsToAssign);

	UserCredits updateUserCredits(String userEmail, int balanceCredit, int usedCredit);

	CreditTransaction saveCreditTransaction(CreditTransaction transaction);

	List<CreditTransaction> getCreditTransactionsByUser(String userEmail);

	String createUserCredits(UserDTO userCreditsDTO);

}
