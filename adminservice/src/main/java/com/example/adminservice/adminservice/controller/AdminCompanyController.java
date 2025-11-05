package com.example.adminservice.adminservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminservice.adminservice.dto.AssignCreditsRequest;
import com.example.adminservice.adminservice.dto.CompanyCreditsDTO;
import com.example.adminservice.adminservice.dto.CompanyRequestDTO;
import com.example.adminservice.adminservice.modal.CompanyCredits;
import com.example.adminservice.adminservice.modal.CompanyTransactions;
import com.example.adminservice.adminservice.service.CompanyCreditService;

@RestController
@RequestMapping("/api/CompanyCredits")
public class AdminCompanyController {

	@Autowired
	private CompanyCreditService companyCreditService;

	@PostMapping("/sync-company")
	public ResponseEntity<String> addCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
		String result = companyCreditService.createCompany(companyRequestDTO);
		return ResponseEntity.ok(result);
	}

//	@GetMapping("/reset-monthly-free-credits")
//	public String resetMonthlyFreeCreditsManually() {
//		companyCreditService.resetMonthlyFreeCredits();
//		return "✅ Free credits reset successfully (manual trigger).";
//	}

	@GetMapping("/allCompanyCredits")
	public ResponseEntity<List<CompanyCredits>> getAllSyncedComapnies() {
		return ResponseEntity.ok(companyCreditService.getAllCompanyCredits());
	}

	@PutMapping("/assign-credits-company")
	public ResponseEntity<String> assignCredits(@RequestBody AssignCreditsRequest request) {
		System.out.println(request.getCreditsToAssign() + " ---" + request.getCompanyName());
		companyCreditService.assignCreditsToCompany(request.getCompanyName(), request.getCreditsToAssign(),request.getAssignCPU());
		return ResponseEntity.ok("Credits assigned successfully");
	}

	@GetMapping("/getcompanyCredits")
	public CompanyCreditsDTO getCreditsByCompanyName(@RequestParam String companyName) {
		return companyCreditService.getCreditsByCompanyName(companyName);
	}

	@PutMapping("/updateCompanyCredits")
	public ResponseEntity<CompanyCredits> updateCompanyCredits(@RequestParam String companyName,
			@RequestBody Map<String, Integer> updateData) {

		if (!updateData.containsKey("balanceCredit") || !updateData.containsKey("usedCredit")) {
			return ResponseEntity.badRequest().build();
		}

		int balanceCredit = updateData.get("balanceCredit");
		int usedCredit = updateData.get("usedCredit");

		CompanyCredits updated = companyCreditService.updateCompanyCredits(companyName, balanceCredit, usedCredit);
		return ResponseEntity.ok(updated);
	}

	@PostMapping("/companyTransactions")
	public ResponseEntity<CompanyTransactions> saveCreditTransaction(@RequestBody CompanyTransactions transaction) {
		CompanyTransactions saved = companyCreditService.saveCreditTransaction(transaction);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/getCredtTransactionsByCompany")
	public ResponseEntity<List<CompanyTransactions>> getCreditTransactionsByCompany(@RequestParam String companyName) {
		List<CompanyTransactions> transactions = companyCreditService.getCreditTransactionsByUser(companyName);
		return ResponseEntity.ok(transactions);
	}

}
