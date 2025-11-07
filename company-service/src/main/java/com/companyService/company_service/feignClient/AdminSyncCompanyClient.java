package com.companyService.company_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.companyService.company_service.dto.CompanyRequestDTO;

@FeignClient(name = "adminservice", path = "/api/CompanyCredits")
public interface AdminSyncCompanyClient {
	
	@PostMapping("/sync-company")
    ResponseEntity<String> createCompanyCredits(@RequestBody CompanyRequestDTO companyRequestDTO);

}
