package com.example.e_signing_project.authservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.e_signing_project.authservice.dto.RegisterRequest;
import com.example.e_signing_project.authservice.dto.UserDTO;
import com.example.e_signing_project.authservice.model.User;

@Service
public interface AuthService {

	String registerUser(RegisterRequest request);

	User getUserByEmail(String userEmail);

	User getUserDetailsByEmail(String email);

	List<UserDTO> getAllUsers();

	String googleLogin(String idToken);

}
