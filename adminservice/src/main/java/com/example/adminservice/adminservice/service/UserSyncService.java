package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.dto.UserCreditsDTO;
import com.example.adminservice.adminservice.modal.UserCredits;

public interface UserSyncService {

	void syncUsers();

	List<UserCredits> getAllUserCredits();

	UserCreditsDTO getUserCreditsByEmail(String userEmail);


}
