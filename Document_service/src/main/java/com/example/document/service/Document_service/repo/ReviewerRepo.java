package com.example.document.service.Document_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.document.service.Document_service.dto.ReviewedConsentsDTO;
import com.example.document.service.Document_service.entity.Reviewer;

public interface ReviewerRepo extends JpaRepository<Reviewer, Long> {

//	List<Reviewer> findByReviewerEmail(String reviewerEmail);

	@Query("SELECT new com.example.document.service.Document_service.dto.ReviewedConsentsDTO(d.id,d.documentName, d.companyName, d.senderEmail, d.editedFile) "
			+ "FROM Document d " + "JOIN d.reviewers r "
			+ "WHERE r.reviewerEmail = :reviewerEmail AND r.approved = false")
	List<ReviewedConsentsDTO> findUnreviewedDocumentsByReviewerEmail(String reviewerEmail);

	@Query("SELECT new com.example.document.service.Document_service.dto.ReviewedConsentsDTO(d.id,d.documentName, d.companyName, d.senderEmail, d.editedFile,r.approvedAt) "
			+ "FROM Document d " + "JOIN d.reviewers r "
			+ "WHERE r.reviewerEmail = :reviewerEmail AND r.approved = true")
	List<ReviewedConsentsDTO> findReviewedDocumentsByReviewerEmail(String reviewerEmail);

	Reviewer findByReviewerEmailAndDocumentId(String reviewerEmail, long documentId);

	List<Reviewer> findByDocumentId(long documentId);

}
