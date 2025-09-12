package com.example.adminservice.adminservice.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.adminservice.adminservice.dto.CreditRequestMessage;
import com.example.adminservice.adminservice.modal.CreditRequest;
import com.example.adminservice.adminservice.repo.CreditRequestRepository;
import com.example.adminservice.adminservice.service.CreditRequestService;

@Service
public class CreditRequestServiceImpl implements CreditRequestService {

	@Autowired
	private CreditRequestRepository creditRequestRepository;

	@Override
	public void saveRequest(CreditRequestMessage message) {
		CreditRequest request = new CreditRequest();
		request.setUserName(message.getUserName());
		request.setUserEmail(message.getUserEmail());
		request.setRequestedCredits(message.getRequestedCredits());
		request.setSeenByAdmin(false); // Default to false
		CreditRequest saved = creditRequestRepository.save(request);

	}

	@Override
	public List<CreditRequest> getUnseenRequests() {
		return creditRequestRepository.findBySeenByAdminFalse();
	}

	@Override
	public void markAsSeen(Long id) {
		CreditRequest request = creditRequestRepository.findById(id).orElseThrow();
		request.setSeenByAdmin(true);
		creditRequestRepository.save(request);

	}

}
