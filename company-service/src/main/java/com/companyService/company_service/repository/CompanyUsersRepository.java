package com.companyService.company_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.companyService.company_service.dto.AssignedCompanyDTO;
import com.companyService.company_service.entity.Company;
import com.companyService.company_service.entity.Company_Users;

public interface CompanyUsersRepository extends JpaRepository<Company_Users, Long> {

	Optional<Company_Users> findByUserEmailAndCompanyId(String userEmail, Long companyId);

	Company_Users findByUserEmailAndInvitedByEmailAndCompany_Id(String userEmail, String invitedByEmail,
			Long companyId);

	List<Company_Users> findByInvitedByEmail(String invitedByEmail);

	List<Company_Users> findByUserEmail(String userEmail);

	@Query("SELECT new com.companyService.company_service.dto.AssignedCompanyDTO(c.company.companyName, c.company.description, c.invitedByEmail, c.role) "
			+ "FROM Company_Users c "
			+ "WHERE c.userEmail = :userEmail AND c.invitationStatus =  com.companyService.company_service.companyuserEnum.InvitationStatus.ACCEPTED")
	List<AssignedCompanyDTO> findAssignedCompaniesByUserEmail(String userEmail);

	@Query("""
			    SELECT cu
			    FROM Company_Users cu
			    WHERE cu.company.companyName = :companyName
			      AND cu.invitationStatus = com.companyService.company_service.companyuserEnum.InvitationStatus.ACCEPTED
			""")
	List<Company_Users> findAcceptedUsersByCompanyName(String companyName);

}
