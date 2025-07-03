package com.example.document.service.Document_service.entity;

import java.util.Arrays;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Sign_Document {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String receiventEmail;
	private String signerEmail;
	@Lob
	private byte[] signDocument;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReceiventEmail() {
		return receiventEmail;
	}
	public void setReceiventEmail(String receiventEmail) {
		this.receiventEmail = receiventEmail;
	}
	public String getSignerEmail() {
		return signerEmail;
	}
	public void setSignerEmail(String signerEmail) {
		this.signerEmail = signerEmail;
	}
	public byte[] getSignDocument() {
		return signDocument;
	}
	
	
	@Override
	public String toString() {
		return "Sign_Document [id=" + id + ", receiventEmail=" + receiventEmail + ", signerEmail=" + signerEmail
				+ ", signDocument=" + Arrays.toString(signDocument) + "]";
	}
	public void setSignDocument(byte[] signDocument) {
		this.signDocument = signDocument;
	}
	
	
	

}
