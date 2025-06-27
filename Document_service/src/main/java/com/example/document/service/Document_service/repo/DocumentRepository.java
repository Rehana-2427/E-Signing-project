package com.example.document.service.Document_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document.service.Document_service.entity.Document;

public interface DocumentRepository extends JpaRepository<Document, Long>{

}
