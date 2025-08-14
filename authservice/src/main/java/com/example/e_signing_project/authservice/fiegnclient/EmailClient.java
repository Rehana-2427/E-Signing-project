package com.example.e_signing_project.authservice.fiegnclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.e_signing_project.authservice.model.EmailRequest;

@FeignClient(name = "email-service", path = "/api/email")
public interface EmailClient {

    @PostMapping("/sendotp")
    ResponseEntity<String> sendEmail(@RequestBody EmailRequest request);
}
