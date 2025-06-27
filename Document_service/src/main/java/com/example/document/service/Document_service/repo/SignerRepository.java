package com.example.document.service.Document_service.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;

public interface SignerRepository extends JpaRepository<Signer, Long>{

	Optional<Document> findById(Signer signer_id);

}
