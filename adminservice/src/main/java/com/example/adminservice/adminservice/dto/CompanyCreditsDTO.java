package com.example.adminservice.adminservice.dto;

public class CompanyCreditsDTO {
	private Integer freeCreidts = 10;
	private Integer paidcredits = 0;
	private Integer balanceCredit = 0;
	private Integer usedCredit = 0;
	private Integer creditPriceUnit = 5;

	public CompanyCreditsDTO(Integer freeCreidts, Integer paidcredits, Integer balanceCredit, Integer usedCredit,
			Integer creditPriceUnit) {
		super();
		this.freeCreidts = freeCreidts;
		this.paidcredits = paidcredits;
		this.balanceCredit = balanceCredit;
		this.usedCredit = usedCredit;
		this.creditPriceUnit = creditPriceUnit;
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

	public Integer getCreditPriceUnit() {
		return creditPriceUnit;
	}

	public void setCreditPriceUnit(Integer creditPriceUnit) {
		this.creditPriceUnit = creditPriceUnit;
	}

}
