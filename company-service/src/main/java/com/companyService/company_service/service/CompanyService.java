package com.companyService.company_service.service;

import java.util.List;

import com.companyService.company_service.dto.CompanyRequestDTO;
import com.companyService.company_service.entity.Company;

public interface CompanyService {

	void createCompany(CompanyRequestDTO companyRequestDTO);

	List<Company> getCompaniesByAdminEmail(String email);


}
