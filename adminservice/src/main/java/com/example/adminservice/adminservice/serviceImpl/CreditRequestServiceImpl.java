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
		boolean isCompanyRequest = message.getCompanyName() != null && !message.getCompanyName().isEmpty();
		if (isCompanyRequest) {
			request.setCompanyName(message.getCompanyName());
			request.setUserEmail(message.getUserEmail());
			request.setUserName(message.getUserName());
		} else {
			if (message.getUserName() == null || message.getUserEmail() == null) {
				throw new IllegalArgumentException("User name and email are required for user requests");
			}
			request.setUserName(message.getUserName());
			request.setUserEmail(message.getUserEmail());
		}
	
		request.setRequestedCredits(message.getRequestedCredits());
		request.setSeenByAdmin(false);
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
