package com.companyService.company_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.companyService.company_service.dto.CompanyRequestDTO;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@PostMapping("/addCompany")
	public ResponseEntity<String> createCompany(@RequestBody CompanyRequestDTO companyRequestDTO) {
		companyService.createCompany(companyRequestDTO);
		return ResponseEntity.ok("Company created successfully.");
	}

	@GetMapping("/getCompanyByAdminEmail")
	public ResponseEntity<List<Company>> getCompaniesByAdminEmail(@RequestParam String email) {
		List<Company> companies = companyService.getCompaniesByAdminEmail(email);
		return ResponseEntity.ok(companies);
	}

	

}
