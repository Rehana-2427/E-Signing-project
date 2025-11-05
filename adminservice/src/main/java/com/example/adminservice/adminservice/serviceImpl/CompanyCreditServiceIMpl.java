package com.example.adminservice.adminservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ctc.wstx.sr.CompactNsContext;
import com.example.adminservice.adminservice.dto.CompanyCreditsDTO;
import com.example.adminservice.adminservice.dto.CompanyRequestDTO;
import com.example.adminservice.adminservice.dto.UserCreditsDTO;
import com.example.adminservice.adminservice.modal.CompanyCredits;
import com.example.adminservice.adminservice.modal.CompanyTransactions;
import com.example.adminservice.adminservice.modal.UserCredits;
import com.example.adminservice.adminservice.repo.CompanyCreditsRepo;
import com.example.adminservice.adminservice.repo.CompanyTransactionRepo;
import com.example.adminservice.adminservice.service.CompanyCreditService;

@Service
public class CompanyCreditServiceIMpl implements CompanyCreditService {

	@Autowired
	private CompanyCreditsRepo companyCreditsRepo;

	@Autowired
	private CompanyTransactionRepo companyTransactionRepo;

	@Override
	public String createCompany(CompanyRequestDTO companyRequestDTO) {
		CompanyCredits existing = companyCreditsRepo.findByCompanyNameIgnoreCase(companyRequestDTO.getCompanyName());
		if (existing == null) {
			CompanyCredits newCompany = new CompanyCredits();
			newCompany.setCompanyName(companyRequestDTO.getCompanyName().trim());
			newCompany.setDescription(companyRequestDTO.getDescription());
			newCompany.setAdminEmail(companyRequestDTO.getAdminEmail());
			newCompany.setAdminUserName(companyRequestDTO.getAdminUserName());
			newCompany.setFreeCreidts(10);
			newCompany.setPaidcredits(0);
			newCompany.setBalanceCredit(10);
			newCompany.setUpdatedAt(LocalDate.now());
			companyCreditsRepo.save(newCompany);
			return "company Saved Succesfully";

		} else {
			return "company already exists";
		}
	}

	@Override
	public List<CompanyCredits> getAllCompanyCredits() {
		return companyCreditsRepo.findAll();
	}

	@Override
	public void assignCreditsToCompany(String companyName, int creditsToAssign,int assignCPU) {
		// TODO Auto-generated method stub
		CompanyCredits existingCompany = companyCreditsRepo.findByCompanyNameIgnoreCase(companyName);
		if (existingCompany == null) {
			throw new RuntimeException("Company not found with companyName: " + companyName);
		}
		boolean isFirstTime = existingCompany.getPaidcredits() == 0;
		int newCredits = existingCompany.getPaidcredits() + creditsToAssign;
		int balance = (newCredits + existingCompany.getFreeCreidts()) - (existingCompany.getUsedCredit());
		existingCompany.setPaidcredits(newCredits);
		existingCompany.setBalanceCredit(balance);
		existingCompany.setUpdatedAt(LocalDate.now());
		int newCPU = existingCompany.getCreditPriceUnit() + assignCPU;
		existingCompany.setCreditPriceUnit(newCPU);
		companyCreditsRepo.save(existingCompany);

	}

	@Override
	public CompanyCreditsDTO getCreditsByCompanyName(String companyName) {
		CompanyCredits company = companyCreditsRepo.findByCompanyNameIgnoreCase(companyName);
		if (company == null) {
			throw new RuntimeException("company not found with companyName: " + companyName);
		}

		return new CompanyCreditsDTO(company.getFreeCreidts(), company.getPaidcredits(), company.getBalanceCredit(),
				company.getUsedCredit(),company.getCreditPriceUnit());
	}

	@Override
	public CompanyCredits updateCompanyCredits(String companyName, int balanceCredit, int usedCredit) {
		// TODO Auto-generated method stub
		CompanyCredits companyCredits = companyCreditsRepo.findByCompanyNameIgnoreCase(companyName);
		// Calculate how many credits should be deducted
		int freeCredits = companyCredits.getFreeCreidts();
		int paidCredits = companyCredits.getPaidcredits();
		// Deduct free credits first
		if (usedCredit <= freeCredits) {
			// All used credits can be deducted from free credits
			companyCredits.setFreeCreidts(freeCredits - usedCredit);
		} else {
			// Use up all free credits and deduct from paid credits
			companyCredits.setFreeCreidts(0);
			int remainingCredits = usedCredit - freeCredits;

			// Deduct remaining credits from paid credits
			if (remainingCredits <= paidCredits) {
				companyCredits.setPaidcredits(paidCredits - remainingCredits);
			} else {
				// If not enough paid credits, update to 0 or handle according to your policy
				companyCredits.setPaidcredits(0);
			}
		}

		// Update balance and used credits after deduction
		companyCredits.setBalanceCredit(balanceCredit - usedCredit);
		companyCredits.setUsedCredit(usedCredit);
		companyCredits.setUpdatedAt(LocalDate.now());
//		companyCredits.setBalanceCredit(balanceCredit);
//		companyCredits.setUsedCredit(usedCredit);
//		companyCredits.setUpdatedAt(LocalDate.now());
		return companyCreditsRepo.save(companyCredits);
	}

	@Override
	public CompanyTransactions saveCreditTransaction(CompanyTransactions transaction) {
		transaction.setDate(LocalDate.now());
		return companyTransactionRepo.save(transaction);
	}

	@Override
	public List<CompanyTransactions> getCreditTransactionsByUser(String companyName) {
		// TODO Auto-generated method stub
		return companyTransactionRepo.findByCompanyName(companyName);
	}

	@Scheduled(cron = "0 0 0 1 * ?")
	// @Scheduled(cron = "0 0 0 1 * ?") ==> 1st 0 - minute , 0 0 0 - Every day at
	// midnight ,
	// "0 0 9 ? * MON - Every Monday at 9 AM , 0 0 0 1 * ?" 1st day of every month
	public void resetMonthlyFreeCredits() {
		List<CompanyCredits> companies = companyCreditsRepo.findAll();

		for (CompanyCredits c : companies) {
			c.setFreeCreidts(10);
			c.setUsedCredit(0);
	        int newBalanceCredits = c.getPaidcredits() + c.getFreeCreidts();
	        c.setBalanceCredit(newBalanceCredits);

			c.setUpdatedAt(LocalDate.now());
		}

		companyCreditsRepo.saveAll(companies);
		System.out.println("✅ Monthly free credits reset for all companies!");
	}

}
