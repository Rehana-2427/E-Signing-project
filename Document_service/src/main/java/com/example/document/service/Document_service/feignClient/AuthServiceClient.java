package com.example.document.service.Document_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.document.service.Document_service.dto.UserDTO;

@FeignClient(name = "authservice") // Must match Eureka registration name
public interface AuthServiceClient {

    @GetMapping("/api/auth/by-email")
    UserDTO getUserByEmail(@RequestParam String email);
}
