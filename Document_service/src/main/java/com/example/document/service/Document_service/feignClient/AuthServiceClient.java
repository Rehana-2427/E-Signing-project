package com.example.document.service.Document_service.feignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.document.service.Document_service.dto.UserDTO;

@FeignClient(name = "authservice") 
public interface AuthServiceClient {

    @GetMapping("/userNameByEmail")
    UserDTO getUserByEmail(@RequestParam String userEmail);
}
