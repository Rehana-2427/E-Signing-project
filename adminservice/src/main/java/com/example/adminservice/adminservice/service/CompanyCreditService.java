package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.dto.CompanyCreditsDTO;
import com.example.adminservice.adminservice.dto.CompanyRequestDTO;
import com.example.adminservice.adminservice.modal.CompanyCredits;

public interface CompanyCreditService {

	String createCompany(CompanyRequestDTO companyRequestDTO);

	List<CompanyCredits> getAllCompanyCredits();

	void assignCreditsToCompany(String companyName, int creditsToAssign);

	CompanyCreditsDTO getCreditsByCompanyName(String companyName);

}
