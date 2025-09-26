package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerInfo;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.dto.SignerStatusResponse;
import com.example.document.service.Document_service.dto.SignersContact;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.repo.DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.SignerService;
import com.example.document.service.Document_service.utility.PdfUtils;

@Service
public class SignerServiceImpl implements SignerService {

	@Autowired
	private SignerRepository signerRepository;
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private EmailClient emailClient;

	@Override
	@Transactional
	public void updateSignerStatus(SignerRequest request) {
		Signer signer = signerRepository.findByEmailAndDocumentId(request.getEmail(), request.getDocumentId());

		if (signer == null) {
			throw new RuntimeException("Signer not found");
		}

		// Update current signer
		signer.setSignStatus(request.getSignStatus());

		if ("completed".equalsIgnoreCase(request.getSignStatus())) {
			signer.setSignedAt(LocalDate.now());
		}

		byte[] signedFileBytes = null;
		if (request.getSigned_file() != null && !request.getSigned_file().isEmpty()) {
			signedFileBytes = Base64.getDecoder().decode(request.getSigned_file());
			signer.setSigned_file(signedFileBytes);
		}

		signerRepository.save(signer); // Save current signer first

		Document document = documentRepository.findById(request.getDocumentId())
				.orElseThrow(() -> new RuntimeException("Document not found"));

		String mode = document.getSigningMode();

		if ("same_doc_pages".equalsIgnoreCase(mode) || "same_doc_end".equalsIgnoreCase(mode)) {
			if ("completed".equalsIgnoreCase(request.getSignStatus()) || signedFileBytes != null) {
				List<Signer> allSigners = document.getSigners();
				for (Signer s : allSigners) {
					s.setSigned_file(signedFileBytes);
					// Don't overwrite status or signedAt for other signers who are still pending
				}
				signerRepository.saveAll(allSigners);
			}

			notifyOtherRecipients(document, signer); // 🔁 Other signers only
			notifySenderAboutSigner(document, signer); // ✅ Update email to sender
//			sendCompletionReport(document);

			if (allSignersCompleted(document)) {
				sendCompletionReport(document);
			}
		} else if ("multi_doc".equalsIgnoreCase(mode)) {
			sendMultiDocEmail(document, signer);
		}
	}

	private void sendMultiDocEmail(Document document, Signer signer) {
		// TODO Auto-generated method stub
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setTo(document.getSenderEmail());
		emailRequest.setSenderName(signer.getName());
		emailRequest.setSenderEmail(signer.getEmail());
		emailRequest.setTitle(document.getDocumentName());
		emailRequest.setSignedAt(signer.getSignedAt());
		emailClient.sendUpdateStatusEmail(emailRequest);
	}

	private boolean allSignersCompleted(Document doc) {
		List<Signer> list = doc.getSigners();
		return list != null && !list.isEmpty()
				&& list.stream().allMatch(s -> "completed".equalsIgnoreCase(s.getSignStatus()));
	}

	private void notifyOtherRecipients(Document doc, Signer currentSigner) {
		List<Signer> allSigners = doc.getSigners();

		List<String> recipientEmails = allSigners.stream().map(Signer::getEmail)
				.filter(email -> !email.equalsIgnoreCase(currentSigner.getEmail())).distinct()
				.collect(Collectors.toList());

		for (String email : recipientEmails) {
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setTo(email);
			emailRequest.setSenderName(currentSigner.getName());
			emailRequest.setSenderEmail(currentSigner.getEmail());
			emailRequest.setTitle(doc.getDocumentName());
			emailRequest.setSignedAt(currentSigner.getSignedAt());

			emailClient.sendUpdateStatusEmailToAll(emailRequest);
		}
	}

	private void notifySenderAboutSigner(Document doc, Signer currentSigner) {
		EmailRequest emailRequest = new EmailRequest();
		emailRequest.setTo(doc.getSenderEmail());
		emailRequest.setSenderName(currentSigner.getName());
		emailRequest.setSenderEmail(currentSigner.getEmail());
		emailRequest.setTitle(doc.getDocumentName());
		emailRequest.setSignedAt(currentSigner.getSignedAt());

		emailClient.sendUpdateStatusEmail(emailRequest); // Use the existing email method
	}

	@Transactional
	public void sendCompletionReport(Document document) {

		List<Signer> signers = document.getSigners();

		// Build List<SignerInfo> for email template
		List<SignerInfo> signerInfos = signers.stream().map(s -> {
			SignerInfo info = new SignerInfo();
			info.setName(s.getName());
			info.setEmail(s.getEmail());
			info.setSignedAt(s.getSignedAt());
			return info;
		}).collect(Collectors.toList());

		// Prepare recipients emails: all signers + sender
		Set<String> recipientEmails = new HashSet();
		for (Signer s : signers) {
			recipientEmails.add(s.getEmail());
		}
		recipientEmails.add(document.getSenderEmail());

		// Send email to each recipient separately
		for (String toEmail : recipientEmails) {
			EmailRequest emailRequest = new EmailRequest();
			emailRequest.setTo(toEmail);
			emailRequest.setTitle(document.getDocumentName());
			emailRequest.setSenderEmail(document.getSenderEmail());
			emailRequest.setSigners(signerInfos);

			// call email microservice via Feign client
			emailClient.sendSummaryEmail(emailRequest);
		}
	}

	@Override
	public List<MyDocumentDTO> getDocumentsByEmail(String email) {
		return convertToDto(signerRepository.findByEmail(email));
	}

	@Override
	public List<MyDocumentDTO> getCompletedDocumentsByEmail(String email) {
		return convertToDto(signerRepository.findCompletedByEmail(email));
	}

//	@Override
//	public List<MyDocumentDTO> getSearchPendingDocs(String email, String query) {
//	    List<Signer> signers = signerRepository.findPendingDocumentsByEmailAndQuery(email, query);
//	    return convertToDto(signers);
//	}

	@Override
	public List<MyDocumentDTO> getSearchPendingDocs(String email, String query) {
		List<Signer> signers = signerRepository.findPendingDocumentsByEmailOnly(email); // exclude query here

		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
			String finalQuery = query.toLowerCase();

			signers = signers.stream().filter(signer -> {
				Document doc = signer.getDocument();
				boolean nameMatch = doc.getDocumentName() != null
						&& doc.getDocumentName().toLowerCase().contains(finalQuery);

				boolean contentMatch = false;
				if (doc.getEditedFile() != null && doc.getEditedFile().length > 0) {
					try {
						String extractedText = PdfUtils.extractTextFromPdf(doc.getEditedFile());
						contentMatch = extractedText.toLowerCase().contains(finalQuery);
					} catch (Exception e) {
						System.err.println("Failed to extract PDF content: " + e.getMessage());
					}
				}

				return nameMatch || contentMatch;
			}).collect(Collectors.toList());
		}

		return convertToDto(signers);
	}

//	@Override
//	public List<MyDocumentDTO> getSearchCompletedDocs(String email, String query) {
//		List<Signer> signers = signerRepository.findCompletedDocumentsByEmailAndQuery(email, query);
//		return convertToDto(signers);
//	}
	
	@Override
	public List<MyDocumentDTO> getSearchCompletedDocs(String email, String query) {
		List<Signer> signers = signerRepository.findCompletedDocumentsByEmailOnly(email);

		if (query != null && !query.trim().isEmpty() && !"no due date".equalsIgnoreCase(query.trim())) {
			String finalQuery = query.toLowerCase();

			signers = signers.stream().filter(signer -> {
				Document doc = signer.getDocument();
				boolean nameMatch = doc.getDocumentName() != null
						&& doc.getDocumentName().toLowerCase().contains(finalQuery);

				boolean contentMatch = false;
				if (doc.getEditedFile() != null && doc.getEditedFile().length > 0) {
					try {
						String extractedText = PdfUtils.extractTextFromPdf(doc.getEditedFile());
						contentMatch = extractedText.toLowerCase().contains(finalQuery);
					} catch (Exception e) {
						System.err.println("Failed to extract PDF content: " + e.getMessage());
					}
				}

				return nameMatch || contentMatch;
			}).collect(Collectors.toList());
		}

		return convertToDto(signers);
	}

	private List<MyDocumentDTO> convertToDto(List<Signer> signers) {
		LocalDate now = LocalDate.now();

		return signers.stream().filter(signer -> {
			Document doc = signer.getDocument();
			return doc.getDeadline() == null || doc.getDeadline().isAfter(now);
		}).map(signer -> {
			Document doc = signer.getDocument();
			return new MyDocumentDTO(doc.getId(), doc.getDocumentName(), doc.getCreatedDate(), signer.getSignedAt(),
					signer.getSignStatus(), signer.getSigned_file());
		}).collect(Collectors.toList());
	}

	@Override
	public List<SignersContact> getSignerStats(String senderEmail) {
		return signerRepository.findSignerStatsBySender(senderEmail);
	}

	@Override
	public List<SignerStatusResponse> getSignerStatusForDocument(Long documentId) {
		// TODO Auto-generated method stub
		return signerRepository.findSignersByDocumentId(documentId);
	}

}
