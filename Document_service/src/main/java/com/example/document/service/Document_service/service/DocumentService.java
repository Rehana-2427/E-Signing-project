package com.example.document.service.Document_service.service;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.example.document.service.Document_service.entity.Document;

public interface DocumentService {


	Document saveDocument(String title, String termsType, String termsLink, MultipartFile pdfFile,
			MultipartFile termsPdfFile, String signersJson, String senderEmail, LocalDate signRequiredBy);

}
