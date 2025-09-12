package com.example.e_signing.email_service.email_service.service;

import org.springframework.stereotype.Service;

import com.example.e_signing.email_service.email_service.request.EmailCreditResponse;
import com.example.e_signing.email_service.email_service.request.EmailRequest;
import com.example.e_signing.email_service.email_service.request.ReportRequestDto;

import jakarta.mail.MessagingException;

@Service
public interface EmailService {

	public void sendEmail(EmailRequest request);


	public void sendDocumentEmails(EmailRequest request) throws Exception;


	public void sendGroupDocumentEmails(EmailRequest request) throws Exception;


	public void sendUpdateStatusEmail(EmailRequest request) throws MessagingException;


	public void sendUpdateStatusEmailtoAll(EmailRequest req) throws MessagingException;


	public void sendSummaryEmail(EmailRequest req) throws MessagingException;


	public void sendReminderEmail(EmailRequest request) throws MessagingException;


	public void sendAssignedCreditsEmail(EmailCreditResponse request);


	public void sendReportToSignersAndSender(ReportRequestDto reportRequestDto);
}
