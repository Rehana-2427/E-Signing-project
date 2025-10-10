package com.example.adminservice.adminservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ctc.wstx.sr.CompactNsContext;
import com.example.adminservice.adminservice.dto.CompanyCreditsDTO;
import com.example.adminservice.adminservice.dto.CompanyRequestDTO;
import com.example.adminservice.adminservice.dto.UserCreditsDTO;
import com.example.adminservice.adminservice.modal.CompanyCredits;
import com.example.adminservice.adminservice.modal.UserCredits;
import com.example.adminservice.adminservice.repo.CompanyCreditsRepo;
import com.example.adminservice.adminservice.service.CompanyCreditService;

@Service
public class CompanyCreditServiceIMpl implements CompanyCreditService{

	@Autowired
	private CompanyCreditsRepo companyCreditsRepo;
	@Override
	public String createCompany(CompanyRequestDTO companyRequestDTO) {
		CompanyCredits existing =  companyCreditsRepo.findByCompanyNameIgnoreCase(companyRequestDTO.getCompanyName());
		if(existing == null) {
			CompanyCredits newCompany = new CompanyCredits();
			newCompany.setCompanyName(companyRequestDTO.getCompanyName().trim());
			newCompany.setDescription(companyRequestDTO.getDescription());
			newCompany.setAdminEmail(companyRequestDTO.getAdminEmail());
			newCompany.setAdminUserName(companyRequestDTO.getAdminUserName());
			newCompany.setFreeCreidts(10);
			newCompany.setPaidcredits(0);
			newCompany.setBalanceCredit(0);
			newCompany.setUpdatedAt(LocalDate.now());
			companyCreditsRepo.save(newCompany);
			return "company Saved Succesfully";
			
		}
		else {
			return "company already exists";
		}
	}

	@Override
	public List<CompanyCredits> getAllCompanyCredits() {
		return companyCreditsRepo.findAll();
	}

	@Override
	public void assignCreditsToCompany(String companyName, int creditsToAssign) {
		// TODO Auto-generated method stub
		CompanyCredits existingCompany =  companyCreditsRepo.findByCompanyNameIgnoreCase(companyName);
		if (existingCompany == null) {
			throw new RuntimeException("Company not found with companyName: " + companyName);
		}
	    boolean isFirstTime = existingCompany.getPaidcredits() == 0;
		int newCredits = existingCompany.getPaidcredits() + creditsToAssign;
		int balance = (newCredits+existingCompany.getFreeCreidts()) - (existingCompany.getUsedCredit());
		existingCompany.setPaidcredits(newCredits);
		existingCompany.setBalanceCredit(balance);
		existingCompany.setUpdatedAt(LocalDate.now());
		companyCreditsRepo.save(existingCompany);

	}


	@Override
	public CompanyCreditsDTO getCreditsByCompanyName(String companyName) {
		CompanyCredits company = companyCreditsRepo.findByCompanyNameIgnoreCase(companyName);
		if(company == null) {
			throw new RuntimeException("company not found with companyName: "+ companyName);
		}
		
		return new CompanyCreditsDTO(company.getFreeCreidts(), company.getPaidcredits(), company.getBalanceCredit(), company.getUsedCredit());
	}

}
