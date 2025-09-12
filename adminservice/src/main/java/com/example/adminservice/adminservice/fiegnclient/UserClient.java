package com.example.adminservice.adminservice.fiegnclient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.adminservice.adminservice.dto.UserDTO;

@FeignClient(name = "authservice", path = "/api/auth")
public interface UserClient {

	@GetMapping("/usersList")
	List<UserDTO> getAllUsers();
}
