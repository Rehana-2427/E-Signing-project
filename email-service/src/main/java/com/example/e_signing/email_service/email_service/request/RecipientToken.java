package com.example.e_signing.email_service.email_service.request;

public class RecipientToken {
	private String name;
	private String email;
	private Long id;
	private String token;

	
	


	public RecipientToken(String name, String email, Long id, String token) {
		super();
		this.name = name;
		this.email = email;
		this.id = id;
		this.token = token;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
