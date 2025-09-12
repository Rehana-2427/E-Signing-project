package com.example.document.service.Document_service.dto;

public class ConsentStatsDto {
	private String month;
	private Long totalSentConsents;
	private Long activeConsents;
	private Long expiredConsents;
	private Long draftConsents;
	private Long totalReceivedConsents;
	private Long completedConsents;
	private Long pendingConsents;

	public ConsentStatsDto(String month, Long documentsSent, Long documentsReceived) {
		this.month = month;
		this.totalSentConsents = documentsSent;
		this.totalReceivedConsents = documentsReceived;
	}

	public ConsentStatsDto() {
		super();
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getTotalSentConsents() {
		return totalSentConsents;
	}

	public void setTotalSentConsents(Long totalSentConsents) {
		this.totalSentConsents = totalSentConsents;
	}

	public Long getActiveConsents() {
		return activeConsents;
	}

	public void setActiveConsents(Long activeConsents) {
		this.activeConsents = activeConsents;
	}

	public Long getExpiredConsents() {
		return expiredConsents;
	}

	public void setExpiredConsents(Long expiredConsents) {
		this.expiredConsents = expiredConsents;
	}

	public Long getDraftConsents() {
		return draftConsents;
	}

	public void setDraftConsents(Long draftConsents) {
		this.draftConsents = draftConsents;
	}

	public Long getTotalReceivedConsents() {
		return totalReceivedConsents;
	}

	public void setTotalReceivedConsents(Long totalReceivedConsents) {
		this.totalReceivedConsents = totalReceivedConsents;
	}

	public Long getCompletedConsents() {
		return completedConsents;
	}

	public void setCompletedConsents(Long completedConsents) {
		this.completedConsents = completedConsents;
	}

	public Long getPendingConsents() {
		return pendingConsents;
	}

	public void setPendingConsents(Long pendingConsents) {
		this.pendingConsents = pendingConsents;
	}

}
