package com.example.document.service.Document_service.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document.service.Document_service.entity.Signer;

public interface SignerRepository extends JpaRepository<Signer, Long>{

	Signer findByEmailAndDocumentId(String email, Long documentId);


}
