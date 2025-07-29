package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.entity.Document;
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

	@Override
	public List<MyDocumentDTO> getDocumentsByEmail(String email) {
		// TODO Auto-generated method stub
		List<Signer> signers = signerRepository.findByEmail(email);
		return signers.stream().map(signer -> {
			Document doc = signer.getDocument();
			return new MyDocumentDTO(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(), signer.getSignedAt(),
					signer.getSignStatus());
		}).collect(Collectors.toList());
	}

}
