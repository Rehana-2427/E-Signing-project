package com.example.adminservice.adminservice.dto;

public class CreditSettingsRequest {
	private int docCost;
	private int signCost;
	private int reviewerCost;

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

	public int getReviewerCost() {
		return reviewerCost;
	}

	public void setReviewerCost(int reviewerCost) {
		this.reviewerCost = reviewerCost;
	}

}
