package com.example.document.service.Document_service.dto;

import java.time.LocalDate;

public class MyConsentResponse {

    private Long documentId;
    private String documentName;
    private LocalDate sentOn; // corresponds to Document.createdDate
    private boolean draft;
    private LocalDate signedOn; // latest sign date if all signed
    private int totalSigners;
    private int signedCount;

    // Constructor initializing all fields
    public MyConsentResponse(Long documentId, String documentName, LocalDate sentOn, Boolean draft,
                             LocalDate signedOn, int totalSigners, int signedCount) {
        this.documentId = documentId;
        this.documentName = documentName;
        this.sentOn = sentOn;
        this.draft = draft != null ? draft : false;
        this.signedOn = signedOn;
        this.totalSigners = totalSigners;
        this.signedCount = signedCount;
    }

    // Getters and setters

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public LocalDate getSentOn() {
        return sentOn;
    }

    public void setSentOn(LocalDate sentOn) {
        this.sentOn = sentOn;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

    public LocalDate getSignedOn() {
        return signedOn;
    }

    public void setSignedOn(LocalDate signedOn) {
        this.signedOn = signedOn;
    }

    public int getTotalSigners() {
        return totalSigners;
    }

    public void setTotalSigners(int totalSigners) {
        this.totalSigners = totalSigners;
    }

    public int getSignedCount() {
        return signedCount;
    }

    public void setSignedCount(int signedCount) {
        this.signedCount = signedCount;
    }

    @Override
    public String toString() {
        return "MyConsentResponse [documentId=" + documentId + ", documentName=" + documentName + ", sentOn=" + sentOn
                + ", draft=" + draft + ", signedOn=" + signedOn + ", totalSigners=" + totalSigners + ", signedCount="
                + signedCount + "]";
    }
}
