package com.example.document.service.Document_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document.service.Document_service.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

	List<Document> findBySenderEmailOrderByCreatedDateDesc(String senderEmail);

//	List<Document> findBySenderEmail(String senderEmail);

}
