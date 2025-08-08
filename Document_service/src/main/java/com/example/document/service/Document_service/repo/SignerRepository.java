package com.example.document.service.Document_service.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.document.service.Document_service.dto.SignerStatusResponse;
import com.example.document.service.Document_service.dto.SignersContact;
import com.example.document.service.Document_service.entity.Signer;

public interface SignerRepository extends JpaRepository<Signer, Long> {

	Signer findByEmailAndDocumentId(String email, Long documentId);

	@Query("SELECT s FROM Signer s WHERE s.email = :email  ORDER BY s.document.createdDate DESC")
	List<Signer> findByEmail(@Param("email") String email);

	@Query("""
			    SELECT new com.example.document.service.Document_service.dto.SignersContact(
			        s.name, s.email,
			        SUM(CASE WHEN s.signStatus = 'COMPLETED' THEN 1 ELSE 0 END)
			    )
			    FROM Signer s
			    JOIN s.document d
			    WHERE d.senderEmail = :senderEmail
			    GROUP BY s.name, s.email
			""")
	List<SignersContact> findSignerStatsBySender(String senderEmail);

	List<SignerStatusResponse> findSignersByDocumentId(Long documentId);

}
