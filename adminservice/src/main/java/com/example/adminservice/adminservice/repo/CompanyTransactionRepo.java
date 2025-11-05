package com.example.adminservice.adminservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adminservice.adminservice.modal.CompanyTransactions;

public interface CompanyTransactionRepo extends JpaRepository<CompanyTransactions, Long> {

	List<CompanyTransactions> findByCompanyName(String companyName);

}
