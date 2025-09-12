package com.example.e_signing_project.authservice.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class UserCredits {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private User user;

	private Integer creditBought = 0;
	private Integer usedCredit = 0;
	private Integer balanceCredit = 50; // default credits

	private LocalDate updatedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
