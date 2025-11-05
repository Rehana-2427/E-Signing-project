package com.example.adminservice.adminservice.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class CompanyCredits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String companyName;
	private String description;
	private String adminUserName;
	private String adminEmail;
	private Integer freeCreidts = 10;
	private Integer paidcredits = 0;
	private Integer balanceCredit = 0;
	private Integer usedCredit = 0;
	private LocalDate updatedAt;
	private Integer creditPriceUnit = 5;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdminUserName() {
		return adminUserName;
	}

	public void setAdminUserName(String adminUserName) {
		this.adminUserName = adminUserName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public Integer getFreeCreidts() {
		return freeCreidts;
	}

	public void setFreeCreidts(Integer freeCreidts) {
		this.freeCreidts = freeCreidts;
	}

	public Integer getPaidcredits() {
		return paidcredits;
	}

	public void setPaidcredits(Integer paidcredits) {
		this.paidcredits = paidcredits;
	}

	public Integer getBalanceCredit() {
		return balanceCredit;
	}

	public void setBalanceCredit(Integer balanceCredit) {
		this.balanceCredit = balanceCredit;
	}

	public Integer getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(Integer usedCredit) {
		this.usedCredit = usedCredit;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Integer getCreditPriceUnit() {
		return creditPriceUnit;
	}

	public void setCreditPriceUnit(Integer creditPriceUnit) {
		this.creditPriceUnit = creditPriceUnit;
	}

}
