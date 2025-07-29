package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.SignerService;

@Service
public class SignerServiceImpl implements SignerService {

	@Autowired
	private SignerRepository signerRepository;

	@Override
	public void updateSignerStatus(SignerRequest request) {
		Signer signer = signerRepository.findByEmailAndDocumentId(request.getEmail(), request.getDocumentId());
				

		signer.setSignStatus(request.getSignStatus());

		if ("completed".equalsIgnoreCase(request.getSignStatus())) {
			signer.setSignedAt(LocalDate.now());
		}

		signerRepository.save(signer);

	}

}
