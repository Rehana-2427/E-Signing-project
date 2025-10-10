package com.example.adminservice.adminservice.controller;

import java.util.List;

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
import com.example.adminservice.adminservice.service.CompanyCreditService;

@RestController
@RequestMapping("/api/CompanyCredits")
public class AdminCompanyController {
	

	@Autowired
	private CompanyCreditService companyCreditService;
	
	@PostMapping("/sync-company")
	public ResponseEntity<String> addCompany(@RequestBody CompanyRequestDTO companyRequestDTO){
		String result =  companyCreditService.createCompany(companyRequestDTO);
		return ResponseEntity.ok(result);
	}
	
	@GetMapping("/allCompanyCredits")
	public ResponseEntity<List<CompanyCredits>> getAllSyncedComapnies() {
		return ResponseEntity.ok(companyCreditService.getAllCompanyCredits());
	}
	
	@PutMapping("/assign-credits-company")
	public ResponseEntity<String> assignCredits(@RequestBody AssignCreditsRequest request) {
		System.out.println(request.getCreditsToAssign() + " ---" + request.getCompanyName());
		companyCreditService.assignCreditsToCompany(request.getCompanyName(), request.getCreditsToAssign());
		return ResponseEntity.ok("Credits assigned successfully");
	}
	
	@GetMapping("/getcompanyCredits")
	public CompanyCreditsDTO getCreditsByCompanyName(@RequestParam String companyName) {
		return companyCreditService.getCreditsByCompanyName(companyName);
	}
} 
