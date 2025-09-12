package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.dto.CreditRequestMessage;
import com.example.adminservice.adminservice.modal.CreditRequest;

public interface CreditRequestService {

	void saveRequest(CreditRequestMessage message);

	List<CreditRequest> getUnseenRequests();

	void markAsSeen(Long id);


}
