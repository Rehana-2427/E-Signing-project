package com.example.adminservice.adminservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adminservice.adminservice.modal.CompanyCredits;

public interface CompanyCreditsRepo extends JpaRepository<CompanyCredits, Long>{

	CompanyCredits findByCompanyNameIgnoreCase(String companyName);

}
