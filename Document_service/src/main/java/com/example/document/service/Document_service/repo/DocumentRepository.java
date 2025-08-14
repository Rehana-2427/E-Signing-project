package com.example.document.service.Document_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.document.service.Document_service.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long> {

//	List<Document> findBySenderEmailOrderByCreatedDateDesc(String senderEmail);

//	List<Document> findBySenderEmail(String senderEmail);

	List<Document> findBySenderEmailAndDraftTrueOrderByCreatedDateDesc(String senderEmail);

	List<Document> findBySenderEmailAndDraftFalseOrderByCreatedDateDesc(String senderEmail);

	@Query("SELECT SUM(d.totalCredits) FROM Document d WHERE d.senderEmail = :senderEmail")
	Integer getTotalCreditsBySenderEmail(String senderEmail);

}
