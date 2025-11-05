package com.example.document.service.Document_service.dto;

public class ReviewerToken {

	private String reviewerEmail;
	private Long id;
	private String token;

	public ReviewerToken(String reviewerEmail, Long id, String token) {
		super();
		this.reviewerEmail = reviewerEmail;
		this.id = id;
		this.token = token;
	}

	public String getReviewerEmail() {
		return reviewerEmail;
	}

	public void setReviewerEmail(String reviewerEmail) {
		this.reviewerEmail = reviewerEmail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
