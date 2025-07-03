package com.example.document.service.Document_service.serviceImpl;

import java.io.IOException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import com.example.document.service.Document_service.entity.Document;

import com.example.document.service.Document_service.entity.Sign_Document;

import com.example.document.service.Document_service.entity.EmailRequest;

import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.repo.DocumentRepository;
import com.example.document.service.Document_service.repo.Sign_DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.DocumentService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private SignerRepository signerRepository;
	
	@Autowired
	private Sign_DocumentRepository sign_DocumentRepository;

	@Autowired
	private EmailClient emailClient;

	@Override
	public Document saveDocument(String title, String termsType, String termsLink, MultipartFile pdfFile,
			MultipartFile termsPdfFile, String signersJson, String senderEmail, LocalDate signRequiredBy) {
		try {
			Document doc = new Document();
			doc.setTitle(title);
			doc.setTermsLink(termsType.equals("link") ? termsLink : null);
			doc.setSenderEmail(senderEmail);
			doc.setSignRequiredBy(signRequiredBy);
			doc.setTermsType(termsType);

			if ("document".equalsIgnoreCase(termsType) && termsPdfFile != null) {
				doc.setPdf(termsPdfFile.getBytes());
			}

//			if (pdfFile != null) {
//				doc.setPdf(pdfFile.getBytes());
//			}

			byte[] pdfBytes = null;
			if (pdfFile != null) {
				pdfBytes = pdfFile.getBytes();
				doc.setPdf(pdfBytes);
			}
			ObjectMapper mapper = new ObjectMapper();
			List<Signer> signers = Arrays.asList(mapper.readValue(signersJson, Signer[].class));
			for (Signer signer : signers) {
				signer.setDocument(doc);
			}

			doc.setSigners(signers);
			Document save = documentRepository.save(doc);
		

			List<String> recipientEmails = signers.stream().map(Signer::getEmail) // adjust method if different
					.collect(Collectors.toList());
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setSenderEmail(senderEmail);
			emailRequest.setRecipientEmails(recipientEmails);
			emailRequest.setTitle(title);
			emailRequest.setSignRequiredBy(signRequiredBy);
			emailRequest.setPdfBytes(pdfBytes);

			emailClient.sendDocumentSigningRequestEmail(emailRequest);

			
			System.out.println(senderEmail + " " + recipientEmails + " "+ title + " "+signRequiredBy + " "+pdfBytes);

			return save;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public List<Document> getDocumentByUserEmail(String userEmail) {
		// TODO Auto-generated method stub

		List<Document> documents=signerRepository.findDocumentsBySignerEmail(userEmail);
		return documents;
	}

	@Override
	public Document findById(Long documentId) {
		// TODO Auto-generated method stub
		return documentRepository.getById(documentId);
	}

	@Override
	public Sign_Document uploadSignedDocument(MultipartFile file, String senderEmail, String userEmail) {
		// TODO Auto-generated method stub
		try {
		Sign_Document document=new Sign_Document();
		document.setReceiventEmail(senderEmail);
		document.setSignerEmail(userEmail);	
		document.setSignDocument(file.getBytes());
		Sign_Document save = sign_DocumentRepository.save(document);
		return save;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}

	@Override
	public List<Sign_Document> getSignDocumentByUserEmail(String userEmail) {
		// TODO Auto-generated method stub
		return sign_DocumentRepository.getSignDocumentByUserEmail(userEmail) ;
	}

	@Override
	public Sign_Document downloadSignDocument(Long id) {
		 Sign_Document optionalDoc = sign_DocumentRepository.getById(id);	   
		return optionalDoc;
	}

}
