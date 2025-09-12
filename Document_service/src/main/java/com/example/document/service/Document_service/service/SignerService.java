package com.example.document.service.Document_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.dto.SignerStatusResponse;
import com.example.document.service.Document_service.dto.SignersContact;
import com.example.document.service.Document_service.entity.Signer;

@Service
public interface SignerService {

	void updateSignerStatus(SignerRequest request);

	List<MyDocumentDTO> getDocumentsByEmail(String email);

	List<SignersContact> getSignerStats(String senderEmail);

	List<SignerStatusResponse> getSignerStatusForDocument(Long documentId);

	List<MyDocumentDTO> getCompletedDocumentsByEmail(String email);


}
