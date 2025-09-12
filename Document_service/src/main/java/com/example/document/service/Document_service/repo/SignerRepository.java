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

	@Query("SELECT s FROM Signer s WHERE s.email = :email AND (s.signStatus IS NULL OR s.signStatus <> 'completed') ORDER BY s.document.createdDate DESC")
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

	@Query("SELECT COUNT(s) FROM Signer s WHERE s.email = :email")
	Long countTotalReceived(String email);

	@Query("SELECT COUNT(s) FROM Signer s WHERE s.email = :email AND s.signStatus = 'COMPLETED'")
	Long countCompleted(String email);

	@Query("SELECT COUNT(s) FROM Signer s WHERE s.email = :email AND (s.signStatus IS NULL OR s.signStatus <> 'COMPLETED')")
	Long countPending(String email);

	@Query("SELECT s FROM Signer s WHERE s.email = :email AND s.signStatus = 'completed' ORDER BY s.document.createdDate DESC")
	List<Signer> findCompletedByEmail(@Param("email") String email);

	@Query("SELECT MONTH(d.createdDate) AS month, COUNT(s) AS totalReceived " + "FROM Signer s JOIN s.document d "
			+ "WHERE s.email = :email " + "GROUP BY MONTH(d.createdDate)")
	List<Object[]> getReceivedConsentStatsByMonth(String email);

	@Query("SELECT COUNT(s) FROM Signer s WHERE s.document.id = :documentId AND s.signStatus != 'SIGNED'")
	long countUnsignedSigners(@Param("documentId") Long documentId);

	List<Signer> findByDocumentId(Long documentId);
}
