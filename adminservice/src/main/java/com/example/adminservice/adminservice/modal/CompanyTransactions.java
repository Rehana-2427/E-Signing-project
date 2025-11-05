package com.example.adminservice.adminservice.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CompanyTransactions {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String companyName;
	private String userEmail;
	private Integer usedCredits;
	private Integer balanceCredits;
	private Integer paidCredits;
	private LocalDate date;

	private String documentId;
	private String documentName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
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

	public Integer getPaidCredits() {
		return paidCredits;
	}

	public void setPaidCredits(Integer paidCredits) {
		this.paidCredits = paidCredits;
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
