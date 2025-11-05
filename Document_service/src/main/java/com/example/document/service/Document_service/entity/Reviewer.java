package com.example.document.service.Document_service.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class Reviewer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String reviewerEmail;

	private Boolean approved = false;

	private LocalDate approvedAt;

	// Many reviewers belong to one document
	@ManyToOne
	@JoinColumn(name = "document_id")
	private Document document;

//	@Lob
//	private byte[] review_File;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

	public Boolean getApproved() {
		return approved;
	}

	public void setApproved(Boolean approved) {
		this.approved = approved;
	}

	public LocalDate getApprovedAt() {
		return approvedAt;
	}

	public void setApprovedAt(LocalDate approvedAt) {
		this.approvedAt = approvedAt;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

//	public byte[] getReview_File() {
//		return review_File;
//	}
//
//	public void setReview_File(byte[] review_File) {
//		this.review_File = review_File;
//	}

}
