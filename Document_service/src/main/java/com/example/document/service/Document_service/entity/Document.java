package com.example.document.service.Document_service.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Document {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private LocalDate signRequiredBy;

	private String termsType; // "document" or "link"

	private String termsLink;

	@Lob
	private byte[] pdf;

	private String senderEmail;

	@OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Signer> signers = new ArrayList<>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}



	public LocalDate getSignRequiredBy() {
		return signRequiredBy;
	}

	public void setSignRequiredBy(LocalDate signRequiredBy) {
		this.signRequiredBy = signRequiredBy;
	}

	public String getTermsType() {
		return termsType;
	}

	public void setTermsType(String termsType) {
		this.termsType = termsType;
	}

	public String getTermsLink() {
		return termsLink;
	}

	public void setTermsLink(String termsLink) {
		this.termsLink = termsLink;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public List<Signer> getSigners() {
		return signers;
	}

	public void setSigners(List<Signer> signers) {
		this.signers = signers;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", title=" + title + ", signRequiredBy=" + signRequiredBy + ", termsType="
				+ termsType + ", termsLink=" + termsLink + ", pdf=" + Arrays.toString(pdf) + ", senderEmail="
				+ senderEmail + ", signers=" + signers + "]";
	}

}
