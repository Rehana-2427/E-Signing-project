package com.example.e_signing.email_service.email_service.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.juli.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.e_signing.email_service.email_service.request.EmailCreditResponse;
import com.example.e_signing.email_service.email_service.request.EmailRequest;
import com.example.e_signing.email_service.email_service.request.RecipientToken;
import com.example.e_signing.email_service.email_service.request.ReportRequestDto;
import com.example.e_signing.email_service.email_service.request.ReviewerToken;
import com.example.e_signing.email_service.email_service.request.SendInviteUserByEmail;
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

	@Autowired
	private Environment environment;

	@Override
	public void sendEmail(EmailRequest emailRequest) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
			System.out.println("Preparing email for: " + emailRequest.getTo());
			System.out.println("Using OTP: " + emailRequest.getOtp());
			helper.setFrom(username);
			helper.setTo(emailRequest.getTo());
			helper.setSubject("Email Verification");
			String htmlBody;

			// Prepare HTML content using Thymeleaf
			Context context = new Context();
			context.setVariable("to", emailRequest.getTo());
			context.setVariable("otp", emailRequest.getOtp());

			String htmlContent = templateEngine.process("otp-email.html", context);
			helper.setText(htmlContent, true);
			System.out.println("calling html");
			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Email sending failed: " + e.getMessage());
		}
	}

	@Override
	public void sendDocumentEmails(EmailRequest request) throws Exception {

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

		String baseUrl;
		String activeProfile = System.getProperty("spring.profiles.active", "live");
		if ("dev".equalsIgnoreCase(activeProfile)) {
			baseUrl = "http://localhost:3003";
		} else {
			baseUrl = "https://app.signbook.co";
		}
		String loginUrl = baseUrl + "/signatory_verification?token=" + recipient.getToken();
		Context context = new Context();
		context.setVariable("recipientEmail", recipient);
		context.setVariable("title", request.getTitle());
		context.setVariable("signRequiredBy", request.getSignRequiredBy());
		context.setVariable("senderEmail", request.getSenderEmail());
		context.setVariable("senderName", request.getSenderName());
		context.setVariable("recipientName", recipient.getName());
		context.setVariable("token", recipient.getToken());
		context.setVariable("loginUrl", loginUrl);
		if (request.getCompanyName() != null && !request.getCompanyName().isEmpty()) {
			context.setVariable("companyName", request.getCompanyName());
		}
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

	@Override
	public void sendUpdateStatusEmail(EmailRequest request) throws MessagingException {
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom("ping@jobbox.one");
		helper.setTo(request.getTo());
		helper.setReplyTo(request.getSenderEmail());
		helper.setSubject("Document Signed Update: " + request.getTitle());

		System.out.println("from" + request.getSenderEmail() + " senderName" + request.getSenderName());
		System.out.println("To" + request.getTo());
		System.out.println("Title" + request.getTitle());
		Context context = new Context();
		context.setVariable("senderName", request.getSenderName());
		context.setVariable("senderEmail", request.getSenderEmail());
		context.setVariable("To", request.getTo());
		context.setVariable("documentTitle", request.getTitle());
		context.setVariable("signedDate", request.getSignedAt());
		String htmlContent = templateEngine.process("update_status_multiDoc.html", context);
		helper.setText(htmlContent, true);
		System.out.println("mail sending ......");
		mailSender.send(message);

	}

	@Override
	public void sendUpdateStatusEmailtoAll(EmailRequest req) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

		helper.setFrom("ping@jobbox.one");
		helper.setTo(req.getTo());
		helper.setReplyTo(req.getSenderEmail());
		helper.setSubject("Document Signed Update: " + req.getTitle());

		Context ctx = new Context();
		ctx.setVariable("currentSignerName", req.getSenderName());
		ctx.setVariable("currentSignerEmail", req.getSenderEmail());
		ctx.setVariable("documentTitle", req.getTitle());
		ctx.setVariable("signedAt", req.getSignedAt());

		String html = templateEngine.process("signer_signed_update.html", ctx);
		helper.setText(html, true);
		mailSender.send(msg);

		System.out.println("From: ping@jobbox.one");
		System.out.println("To: " + req.getTo());
		System.out.println("Reply-To: " + req.getSenderEmail());
		System.out.println("Subject: Document Signed Update: " + req.getTitle());

	}

	@Override
	public void sendSummaryEmail(EmailRequest req) throws MessagingException {
		MimeMessage msg = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

		helper.setFrom("ping@jobbox.one");
		helper.setTo(req.getTo());
		helper.setSubject("Signing Progress: " + req.getTitle());

		Context ctx = new Context();
		ctx.setVariable("documentTitle", req.getTitle());
		ctx.setVariable("signers", req.getSigners());
		ctx.setVariable("summaryStatus", req.getSummaryStatus());
		ctx.setVariable("senderEmail", req.getSenderEmail());
		String html = templateEngine.process("document_completion_summary.html", ctx);
		helper.setText(html, true);
		mailSender.send(msg);
	}

	@Override
	public void sendReminderEmail(EmailRequest request) throws MessagingException {

		for (RecipientToken recipient : request.getRecipients()) {
			sendRemainderEmailsToAllsigners(request, recipient);
		}

	}

	private void sendRemainderEmailsToAllsigners(EmailRequest request, RecipientToken recipient)
			throws MessagingException {
		// TODO Auto-generated method stub
		System.out.println("remainder email");
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("ping@jobbox.one");
		helper.setReplyTo(request.getSenderEmail());
		helper.setTo(recipient.getEmail());
		helper.setSubject("Reminder to Sign Document: " + request.getTitle());
		String baseUrl;
		String activeProfile = System.getProperty("spring.profiles.active", "live");
		if ("dev".equalsIgnoreCase(activeProfile)) {
			baseUrl = "http://localhost:3003";
		} else {
			baseUrl = "https://app.signbook.co";
		}
		System.out.println("from" + request.getSenderEmail());
		System.out.println("to" + recipient.getEmail());
		Context context = new Context();
		context.setVariable("name", recipient.getName());
		context.setVariable("documentName", request.getTitle());
		context.setVariable("senderName", request.getSenderName());
		context.setVariable("signBy", request.getSignRequiredBy());
		context.setVariable("token", recipient.getToken());
		context.setVariable("loginUrl", baseUrl);
		String htmlContent = templateEngine.process("reminder-email.html", context);
		helper.setText(htmlContent, true); // HTML enabled
		mailSender.send(message);
	}

	@Override
	public void sendAssignedCreditsEmail(EmailCreditResponse request) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());

			Context context = new Context();
			context.setVariable("userName", request.getUserName());
			context.setVariable("creditsAssigned", request.getCreditsAssigned());
			context.setVariable("balance", request.getBalance());

			String htmlContent = templateEngine.process("assigned-credits-template.html", context);

			helper.setTo(request.getTo());
			helper.setSubject("Credits Assigned Notification");
			helper.setText(htmlContent, true);
			helper.setFrom(username); // Your SMTP sender email

			mailSender.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send assigned credits email", e);
		}

	}

	@Override
	public void sendReportToSignersAndSender(ReportRequestDto report) {
		Context context = new Context();
		context.setVariable("documentName", report.getDocumentName());
		context.setVariable("signerList", report.getSignerList());

		String htmlContent = templateEngine.process("signing-report", context);

		List<String> recipientEmails = report.getSignerList().stream().map(ReportRequestDto.SignerInfo::getEmail)
				.collect(Collectors.toList());

		recipientEmails.add(report.getSenderEmail());

		for (String email : recipientEmails) {
			sendHtmlEmail(email, "Document Signing Report", htmlContent);
		}

	}

	private void sendHtmlEmail(String to, String subject, String htmlContent) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(htmlContent, true);

			mailSender.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException("Failed to send email to " + to, e);
		}
	}

	@Override
	public void sendInviationEmailToUser(SendInviteUserByEmail sendInviteUserByEmail) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

			helper.setFrom(username);
			helper.setReplyTo(sendInviteUserByEmail.getSenderEmail());
			helper.setTo(sendInviteUserByEmail.getTo());
			helper.setSubject("You're invited to join " + sendInviteUserByEmail.getCompanyName());

			String[] activeProfiles = environment.getActiveProfiles();
			String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : "live";

			String baseUrl = "dev".equalsIgnoreCase(activeProfile) ? "http://localhost:3003"
					: "https://app.signbook.co";

			String approvalUrl = baseUrl + "/approve-invitation?token=" + sendInviteUserByEmail.getToken();

			Context context = new Context();
			context.setVariable("to", sendInviteUserByEmail.getTo());
			context.setVariable("companyName", sendInviteUserByEmail.getCompanyName());
			context.setVariable("senderEmail", sendInviteUserByEmail.getSenderEmail());
			context.setVariable("role", sendInviteUserByEmail.getRole());
			context.setVariable("token", sendInviteUserByEmail.getToken());
			context.setVariable("approvalUrl", approvalUrl);

			// 4. Process the HTML template
			String htmlContent = templateEngine.process("invite-email.html", context);
			helper.setText(htmlContent, true);

			// 5. Send the email
			mailSender.send(mimeMessage);

		} catch (MessagingException e) {
			e.printStackTrace();
			throw new RuntimeException("Email sending failed: " + e.getMessage());
		}
	}

	@Override
	public void sendEmailToReviewers(EmailRequest request) throws MessagingException {
		for (ReviewerToken reviewer : request.getReviewers()) {
			sendReviewerEmail(request, reviewer);
		}

	}

	private void sendReviewerEmail(EmailRequest request, ReviewerToken reviewer) throws MessagingException {
		// TODO Auto-generated method stub
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("ping@jobbox.one");
		helper.setReplyTo(request.getSenderEmail());
		helper.setTo(reviewer.getReviewerEmail());

		helper.setSubject("Action Required: Review Document - " + request.getTitle());
		String baseUrl;
		String[] activeProfiles = environment.getActiveProfiles();
		String activeProfile = activeProfiles.length > 0 ? activeProfiles[0] : "live";
		if ("dev".equalsIgnoreCase(activeProfile)) {
			baseUrl = "http://localhost:3003";
		} else {
			baseUrl = "https://app.signbook.co";
		}
		String loginUrl = baseUrl + "/reviewer_verification?token=" + reviewer.getToken();

		Context context = new Context();
		context.setVariable("reviewerEmail", reviewer.getReviewerEmail());
		context.setVariable("title", request.getTitle());
		context.setVariable("senderEmail", request.getSenderEmail());
		context.setVariable("senderName", request.getSenderName());
		context.setVariable("token", reviewer.getToken());
		context.setVariable("loginUrl", loginUrl);
		if (request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
			context.setVariable("senderIdentity", request.getCompanyName() + " - " + request.getSenderEmail());
		} else {
			context.setVariable("senderIdentity", request.getSenderEmail());
		}
		String htmlContent = templateEngine.process("reviewer-email-template", context);
		helper.setText(htmlContent, true);

		mailSender.send(message);
	}

}

//	@Override
//	public void sendEmailToReviewers(EmailRequest request) {
//		String baseUrl;
//		String activeProfile = System.getProperty("spring.profiles.active", "live");
//		if ("dev".equalsIgnoreCase(activeProfile)) {
//			baseUrl = "http://localhost:3003";
//		} else {
//			baseUrl = "https://app.signbook.co";
//		}
//
//		String loginUrl = baseUrl + "/signin";
//
//		for (String reviewerEmail : request.getReviewerEmails()) {
//			if (reviewerEmail == null || reviewerEmail.trim().isEmpty()) {
//				System.err.println("⚠️ Skipping null or empty reviewer email.");
//				continue;
//			}
//
//			try {
//				MimeMessage message = mailSender.createMimeMessage();
//				MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//
//				helper.setFrom("ping@jobbox.one");
//				helper.setTo(reviewerEmail);
//				helper.setSubject("Action Required: Review Document - " + request.getTitle());
//				helper.setReplyTo(request.getSenderEmail());
//
//				// Prepare the context for Thymeleaf
//				Context context = new Context();
//				context.setVariable("reviewerEmail", reviewerEmail);
//				context.setVariable("senderName", request.getSenderName());
//				context.setVariable("documentTitle", request.getTitle());
//				context.setVariable("loginUrl", loginUrl);
//
//				// Prefer companyName if it's set, otherwise fallback to senderEmail
//				if (request.getCompanyName() != null && !request.getCompanyName().trim().isEmpty()) {
//					context.setVariable("senderIdentity",
//							request.getCompanyName() + " - " + request.getSenderEmail());
//				} else {
//					context.setVariable("senderIdentity", request.getSenderEmail());
//				}
//
//				String htmlContent = templateEngine.process("reviewer-email-template", context);
//				helper.setText(htmlContent, true);
//
//				mailSender.send(message);
//				System.out.println("✅ Email sent to reviewer: " + reviewerEmail);
//			} catch (MessagingException e) {
//				System.err.println("❌ Failed to send email to reviewer: " + reviewerEmail + " -> " + e.getMessage());
//			}
//		}
//
//	}
