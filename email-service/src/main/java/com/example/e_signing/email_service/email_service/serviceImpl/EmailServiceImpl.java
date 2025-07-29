package com.example.e_signing.email_service.email_service.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.e_signing.email_service.email_service.request.EmailRequest;
import com.example.e_signing.email_service.email_service.request.RecipientToken;
import com.example.e_signing.email_service.email_service.service.EmailService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private TemplateEngine templateEngine;

	@Override
	public void sendEmail(EmailRequest emailRequest) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(username);
			helper.setTo(emailRequest.getTo());
			helper.setSubject("Email Verification");
			String htmlBody;

//			if (emailRequest.getTemplateName() != null && !emailRequest.getTemplateName().isEmpty()) {
//				Context context = new Context();
//				context.setVariables(emailRequest.getVariables());
//				htmlBody = templateEngine.process(emailRequest.getTemplateName(), context);
//			} else {
//				htmlBody = emailRequest.getBody(); // fallback
//			}

			// Prepare HTML content using Thymeleaf
			Context context = new Context();
			context.setVariable("to", emailRequest.getTo());
			context.setVariable("otp", emailRequest.getOtp());

			String htmlContent = templateEngine.process("otp-email.html", context);
			helper.setText(htmlContent, true);
//			helper.setText(htmlBody, true);
			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Email sending failed: " + e.getMessage());
		}
	}

	@Override
	public void sendDocumentEmails(EmailRequest request) throws Exception {
		// TODO Auto-generated method stub
//		for (String recipient : request.getRecipientEmails()) {
//			sendIndividualEmail(request, recipient);
//		}
		for (RecipientToken recipient : request.getRecipients()) {
			sendIndividualEmail(request, recipient);
		}

	}

	@Retry(name = "emailRetry")
	@CircuitBreaker(name = "emailServiceCB", fallbackMethod = "fallbackSendEmail")
	private void sendIndividualEmail(EmailRequest request, RecipientToken recipient) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("ping@jobbox.one");
		helper.setReplyTo(request.getSenderEmail());
		helper.setTo(recipient.getEmail());

		helper.setSubject("Action Required: Sign Document - " + request.getTitle());

		// Prepare HTML content using Thymeleaf
		Context context = new Context();
		context.setVariable("recipientEmail", recipient);
		context.setVariable("title", request.getTitle());
		context.setVariable("signRequiredBy", request.getSignRequiredBy());
		context.setVariable("senderEmail", request.getSenderEmail());
		context.setVariable("senderName", request.getSenderName());
		context.setVariable("recipientName", recipient.getName());
		context.setVariable("token", recipient.getToken());
		String htmlContent = templateEngine.process("document_signing_request.html", context);
		helper.setText(htmlContent, true);

		// Attach the PDF
		if (request.getPdfBytes() != null) {
			helper.addAttachment("document.pdf", new ByteArrayResource(request.getPdfBytes()));
		}

		// Debug output
		System.out.println("---- Email Debug Info ----");
		System.out.println("From: ping@jobbox.one");
		System.out.println("Reply-To: " + request.getSenderEmail());
		System.out.println("To: " + recipient.getEmail());
		System.out.println("Title: " + request.getTitle());
		System.out.println("Sign Required By: " + request.getSignRequiredBy());
		System.out.println("PDF Size: " + (request.getPdfBytes() != null ? request.getPdfBytes().length : "No file"));
		System.out.println("--------------------------");

		mailSender.send(message);
	}

	private void fallbackSendEmail(EmailRequest request, String recipient, Throwable t) {
		System.err.println("❌ Circuit Breaker Triggered - Email not sent to: " + recipient);
		System.err.println("Reason: " + t.getMessage());
	}

	@Override
	public void sendGroupDocumentEmails(EmailRequest request) throws Exception {
		// TODO Auto-generated method stub

	}

//	@Override
//	public void sendGroupDocumentEmails(EmailRequest request) throws Exception {
//		MimeMessage message = mailSender.createMimeMessage();
//		MimeMessageHelper helper = new MimeMessageHelper(message, true);
//
//		helper.setFrom("ping@jobbox.one");
//		helper.setReplyTo(request.getSenderEmail());
//
//		// Send to all recipient emails
//		helper.setTo(request.getRecipientEmails().toArray(new String[0]));
//		helper.setSubject("Action Required: Sign Document - " + request.getTitle());
//
//		// Prepare HTML content using Thymeleaf
//		Context context = new Context();
//		context.setVariable("title", request.getTitle());
//		context.setVariable("signRequiredBy", request.getSignRequiredBy());
//		context.setVariable("senderEmail", request.getSenderEmail());
//
//		// Optional: Pass list of all signers if you want to show them in email body
//		context.setVariable("recipientList", request.getRecipientEmails());
//
//		String htmlContent = templateEngine.process("group_document_sign_request.html", context);
//		helper.setText(htmlContent, true);
//
//		// Attach PDF
//		if (request.getPdfBytes() != null) {
//			helper.addAttachment("document.pdf", new ByteArrayResource(request.getPdfBytes()));
//		}
//
//		mailSender.send(message);
//
//	}

}
