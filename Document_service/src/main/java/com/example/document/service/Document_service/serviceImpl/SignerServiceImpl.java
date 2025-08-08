package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;
import java.util.Base64;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.document.service.Document_service.dto.EmailRequest;
import com.example.document.service.Document_service.dto.MyDocumentDTO;
import com.example.document.service.Document_service.dto.SignerRequest;
import com.example.document.service.Document_service.dto.SignerStatusResponse;
import com.example.document.service.Document_service.dto.SignersContact;
import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;
import com.example.document.service.Document_service.feignClient.EmailClient;
import com.example.document.service.Document_service.repo.DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.SignerService;

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
			sendCompletionSummary(document);

			if (allSignersCompleted(document)) {
				sendCompletionSummary(document);
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

	private void sendCompletionSummary(Document doc) {
		List<Signer> signers = doc.getSigners();

		long completedCount = signers.stream().filter(s -> "completed".equalsIgnoreCase(s.getSignStatus())).count();

		long totalCount = signers.size();

		String summaryStatus;
		if (completedCount == totalCount) {
			summaryStatus = "All signers have completed signing.";
		} else {
			summaryStatus = completedCount + " signer" + (completedCount > 1 ? "s" : "") + " out of " + totalCount
					+ " have completed signing.";
		}

		for (Signer signer : signers) {
			EmailRequest payload = new EmailRequest();
			payload.setTo(signer.getEmail());
			payload.setSenderName(doc.getSenderName());
			payload.setSenderEmail(doc.getSenderEmail());
			payload.setTitle(doc.getDocumentName());
			payload.setSignedAt(null);
			payload.setSummaryStatus(summaryStatus); // ✅ Important

			emailClient.sendSummaryEmail(payload);
		}
	}

	@Override
	public List<MyDocumentDTO> getDocumentsByEmail(String email) {
		List<Signer> signers = signerRepository.findByEmail(email);
		LocalDate now = LocalDate.now();
		return signers.stream().filter(signer -> {
			signer.getSigned_file();
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
