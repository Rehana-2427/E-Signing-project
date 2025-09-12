package com.example.e_signing_project.authservice.fiegnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.e_signing_project.authservice.dto.UserDTO;

@FeignClient(name = "adminservice", path = "/api/userCreditsList")
public interface AdminServiceClient {
	@PostMapping("/sync-user")
    ResponseEntity<String> createUserCredits(@RequestBody UserDTO userCreditsDTO);
}
