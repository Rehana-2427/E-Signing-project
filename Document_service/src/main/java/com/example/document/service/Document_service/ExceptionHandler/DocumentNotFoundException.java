package com.example.document.service.Document_service.ExceptionHandler;

public class DocumentNotFoundException extends RuntimeException {
	public DocumentNotFoundException(String message) {
		super(message);
	}
}
