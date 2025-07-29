//package com.example.document.service.Document_service.repo;
//
//import java.util.List;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import com.example.document.service.Document_service.entity.Document;
//import com.example.document.service.Document_service.entity.Sign_Document;
//
//@Repository
//public interface Sign_DocumentRepository extends JpaRepository<Sign_Document, Long> {
//
//	 @Query("SELECT DISTINCT s FROM Sign_Document s WHERE s.receiventEmail = ?1")
//	List<Sign_Document> getSignDocumentByUserEmail(String userEmail);
//
//}
