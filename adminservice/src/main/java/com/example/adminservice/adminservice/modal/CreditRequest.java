package com.example.adminservice.adminservice.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

@Entity
public class CreditRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;
	private String userEmail;
	private int requestedCredits;
	private LocalDate requestedAt;
	private boolean seenByAdmin = false;

	@PrePersist
	public void prePersist() {
		this.requestedAt = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getRequestedCredits() {
		return requestedCredits;
	}

	public void setRequestedCredits(int requestedCredits) {
		this.requestedCredits = requestedCredits;
	}

	public LocalDate getRequestedAt() {
		return requestedAt;
	}

	public void setRequestedAt(LocalDate requestedAt) {
		this.requestedAt = requestedAt;
	}

	public boolean isSeenByAdmin() {
		return seenByAdmin;
	}

	public void setSeenByAdmin(boolean seenByAdmin) {
		this.seenByAdmin = seenByAdmin;
	}

}
