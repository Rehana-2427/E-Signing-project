package com.example.document.service.Document_service.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.document.service.Document_service.entity.Document;
import com.example.document.service.Document_service.entity.Signer;

import feign.Param;

public interface SignerRepository extends JpaRepository<Signer, Long>{

	Optional<Document> findById(Signer signer_id);

	 @Query("SELECT DISTINCT s.document FROM Signer s WHERE s.email = :email")
	    List<Document> findDocumentsBySignerEmail(@Param("email") String email);

}
