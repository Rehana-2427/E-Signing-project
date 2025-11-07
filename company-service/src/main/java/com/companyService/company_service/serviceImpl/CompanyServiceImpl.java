package com.companyService.company_service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.companyService.company_service.dto.CompanyRequestDTO;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.feignClient.AdminSyncCompanyClient;
import com.companyService.company_service.repository.CompanyRepo;
import com.companyService.company_service.service.CompanyService;

@Service
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepo companyRepo;
	
	@Autowired
	private AdminSyncCompanyClient adminSyncCompanyClient;

	@Override
	public void createCompany(CompanyRequestDTO companyRequestDTO) {
		Company company = new Company();
		company.setCompanyName(companyRequestDTO.getCompanyName());
		company.setMobileNumber(companyRequestDTO.getMobileNumber());
		company.setDescription(companyRequestDTO.getDescription());
		company.setAdminUserName(companyRequestDTO.getAdminUserName());
		company.setAdminEmail(companyRequestDTO.getAdminEmail());
		companyRepo.save(company);
		try {
		    adminSyncCompanyClient.createCompanyCredits(companyRequestDTO);
		} catch (Exception e) {
		   System.out.println("Failed to sync company with AdminService");
		}
	}

	
	@Override
	public List<Company> getCompaniesByAdminEmail(String adminEmail) {
		// TODO Auto-generated method stub
        return companyRepo.findByAdminEmail(adminEmail);
	}

}
