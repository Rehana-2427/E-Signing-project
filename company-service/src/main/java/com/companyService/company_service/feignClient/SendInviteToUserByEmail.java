package com.companyService.company_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.companyService.company_service.dto.SendInviteToUser;


@FeignClient(name = "email-service", path = "/api/email")
public interface SendInviteToUserByEmail {

	@PostMapping("/sendInviationEmail")
	ResponseEntity<String> sendEmail(@RequestBody SendInviteToUser sendInviteToUser);

}
