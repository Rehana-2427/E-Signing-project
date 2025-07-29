package com.example.document.service.Document_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.entity.Signer;

@Service
public interface SignerService {

	void updateSignerStatus(SignerRequest request);

	List<MyDocumentDTO> getDocumentsByEmail(String email);


}
