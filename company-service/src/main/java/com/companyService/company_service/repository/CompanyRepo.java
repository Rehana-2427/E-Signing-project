package com.companyService.company_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.companyService.company_service.entity.Company;

public interface CompanyRepo extends JpaRepository<Company,Long>{

	List<Company> findByAdminEmail(String adminEmail);

	Optional<Company> findByCompanyName(String companyName);

}
