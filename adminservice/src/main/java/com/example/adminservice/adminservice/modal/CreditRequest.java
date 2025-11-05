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

	private int requestCPUnit;
	private String companyName;

	private String mobileNumber;

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

	public int getRequestCPUnit() {
		return requestCPUnit;
	}

	public void setRequestCPUnit(int requestCPUnit) {
		this.requestCPUnit = requestCPUnit;
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

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

}
