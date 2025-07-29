package com.example.document.service.Document_service.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "signers")
public class Signer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String email;

	@ManyToOne
	@JoinColumn(name = "document_id")
	private Document document;

	private String signStatus;
	private LocalDate signedAt;

//	private String token;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public LocalDate getSignedAt() {
		return signedAt;
	}

	public void setSignedAt(LocalDate signedAt) {
		this.signedAt = signedAt;
	}

//	public String getToken() {
//		return token;
//	}
//
//	public void setToken(String token) {
//		this.token = token;
//	}

	@Override
	public String toString() {
		return "Signer [id=" + id + ", name=" + name + ", email=" + email + ", document=" + document + ", signStatus="
				+ signStatus + ", signedAt=" + signedAt +  "]";
	}

}
