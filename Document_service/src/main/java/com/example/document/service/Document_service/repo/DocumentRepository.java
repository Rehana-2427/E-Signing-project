package com.example.document.service.Document_service.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.document.service.Document_service.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//	List<Document> findBySenderEmailOrderByCreatedDateDesc(String senderEmail);

//	List<Document> findBySenderEmail(String senderEmail);

	List<Document> findBySenderEmailAndDraftTrueOrderByCreatedDateDesc(String senderEmail);

	List<Document> findBySenderEmailAndDraftFalseOrderByCreatedDateDesc(String senderEmail);

	@Query("SELECT SUM(d.signatoryCharge) FROM Document d WHERE d.senderEmail = :senderEmail")
	Integer getTotalCreditsBySenderEmail(String senderEmail);

	Long countBySenderEmailAndDraftFalse(String senderEmail);

	Long countBySenderEmailAndDraftTrue(String senderEmail);

	@Query("SELECT COUNT(d) FROM Document d WHERE d.senderEmail = :senderEmail AND d.draft = false AND d.deadline >= :today")
	Long countActiveConsents(String senderEmail, LocalDate today);

	@Query("SELECT COUNT(d) FROM Document d WHERE d.senderEmail = :senderEmail AND d.draft = false AND d.deadline < :today")
	Long countExpiredConsents(String senderEmail, LocalDate today);

	@Query("SELECT MONTH(d.createdDate) AS month, COUNT(d) AS totalSent " +
	           "FROM Document d " +
	           "WHERE d.senderEmail = :senderEmail " +
	           "GROUP BY MONTH(d.createdDate)")
	List<Object[]> getSentConsentStatsByMonth(String senderEmail);

}
