package com.example.e_signing_project.authservice.dto;

import com.example.e_signing_project.authservice.model.User;

public class LoginResponse {

	private User user;
	private String token;
	private String errorMessage;
	private String successMessage;

	public LoginResponse(User userDetails, String jwt, String errorMessage, String successMessage) {
		this.user = userDetails;
		this.token = jwt;
		this.errorMessage = errorMessage;
		this.successMessage = successMessage;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(String successMessage) {
		this.successMessage = successMessage;
	}

	@Override
	public String toString() {
		return "LoginResponse [user=" + user + ", token=" + token + ", errorMessage=" + errorMessage
				+ ", successMessage=" + successMessage + "]";
	}

}
