package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.dto.CompanyCreditsDTO;
import com.example.adminservice.adminservice.dto.CompanyRequestDTO;
import com.example.adminservice.adminservice.modal.CompanyCredits;
import com.example.adminservice.adminservice.modal.CompanyTransactions;

public interface CompanyCreditService {

	String createCompany(CompanyRequestDTO companyRequestDTO);

	List<CompanyCredits> getAllCompanyCredits();

	void assignCreditsToCompany(String companyName, int creditsToAssign, int assignCPU);

	CompanyCreditsDTO getCreditsByCompanyName(String companyName);

	CompanyCredits updateCompanyCredits(String companyName, int balanceCredit, int usedCredit);

	CompanyTransactions saveCreditTransaction(CompanyTransactions transaction);

	List<CompanyTransactions> getCreditTransactionsByUser(String companyName);

//	void resetMonthlyFreeCredits();

}
