package com.example.adminservice.adminservice.modal;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserCredits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userEmail;
	private String userName;
	private Integer creditBought = 0;
	private Integer usedCredit = 0;
	private Integer balanceCredit = 0;

	private LocalDate updatedAt;

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getCreditBought() {
		return creditBought;
	}

	public void setCreditBought(Integer creditBought) {
		this.creditBought = creditBought;
	}

	public Integer getUsedCredit() {
		return usedCredit;
	}

	public void setUsedCredit(Integer usedCredit) {
		this.usedCredit = usedCredit;
	}

	public Integer getBalanceCredit() {
		return balanceCredit;
	}

	public void setBalanceCredit(Integer balanceCredit) {
		this.balanceCredit = balanceCredit;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

}
