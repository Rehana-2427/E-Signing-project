package com.example.document.service.Document_service.service;

import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.SignerRequest;

@Service
public interface SignerService {

	void updateSignerStatus(SignerRequest request);


}
