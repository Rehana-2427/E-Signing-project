package com.example.adminservice.adminservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adminservice.adminservice.modal.CreditRequest;

public interface CreditRequestRepository extends JpaRepository<CreditRequest, Long>{

	List<CreditRequest> findBySeenByAdminFalse();

}
