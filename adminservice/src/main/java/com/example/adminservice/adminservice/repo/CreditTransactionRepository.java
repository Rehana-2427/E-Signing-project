package com.example.adminservice.adminservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adminservice.adminservice.modal.CreditTransaction;

@Repository
public interface CreditTransactionRepository extends JpaRepository<CreditTransaction, Long> {
    List<CreditTransaction> findByUserEmail(String userEmail);
}