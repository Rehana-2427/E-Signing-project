package com.example.document.service.Document_service.dto;

public class ParticipantStatusDTO {
	private String email;
	private String status;

	public ParticipantStatusDTO(String email, String status) {
		super();
		this.email = email;
		this.status = status;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
