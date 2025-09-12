package com.example.adminservice.adminservice.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CreditTransaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userEmail;
	private Integer usedCredits;
	private Integer balanceCredits;
	private Integer creditsBought;
	private LocalDate date;

	private String documentId;
	private String documentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Integer getUsedCredits() {
		return usedCredits;
	}

	public void setUsedCredits(Integer usedCredits) {
		this.usedCredits = usedCredits;
	}

	public Integer getBalanceCredits() {
		return balanceCredits;
	}

	public void setBalanceCredits(Integer balanceCredits) {
		this.balanceCredits = balanceCredits;
	}

	public Integer getCreditsBought() {
		return creditsBought;
	}

	public void setCreditsBought(Integer creditsBought) {
		this.creditsBought = creditsBought;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getDocumentId() {
		return documentId;
	}

	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

}
