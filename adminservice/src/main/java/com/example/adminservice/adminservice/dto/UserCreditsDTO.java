package com.example.adminservice.adminservice.dto;

public class UserCreditsDTO {
	private Integer creditBought;
	private Integer usedCredit;
	private Integer balanceCredit;

	public UserCreditsDTO(Integer creditBought, Integer usedCredit, Integer balanceCredit) {
		super();
		this.creditBought = creditBought;
		this.usedCredit = usedCredit;
		this.balanceCredit = balanceCredit;
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

}
