package com.example.adminservice.adminservice.dto;

public class CreditSettingsRequest {
	private int docCost;
	private int signCost;

	public int getDocCost() {
		return docCost;
	}

	public void setDocCost(int docCost) {
		this.docCost = docCost;
	}

	public int getSignCost() {
		return signCost;
	}

	public void setSignCost(int signCost) {
		this.signCost = signCost;
	}

}
