package com.example.e_signing_project.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.e_signing_project.authservice.config.JwtUtil;
import com.example.e_signing_project.authservice.dto.JwtResponse;
import com.example.e_signing_project.authservice.dto.LoginRequest;
import com.example.e_signing_project.authservice.dto.LoginResponse;
import com.example.e_signing_project.authservice.dto.RegisterRequest;
import com.example.e_signing_project.authservice.model.User;
import com.example.e_signing_project.authservice.service.AuthService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:3003" })
public class AuthController {

	@Autowired
	private AuthService authService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@PostMapping("/register")
	public JwtResponse registerUser(@RequestBody RegisterRequest request) {
		String token = authService.registerUser(request);
		return new JwtResponse(token);
	}

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginRequest loginRequest) {
		try {
			String userEmail = loginRequest.getUserEmail();
			String password = loginRequest.getPassword();

			User user = authService.getUserByEmail(userEmail);
			if (user == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
						.body(new LoginResponse(null, null, "User not found", null));
			}

			// Authentication via AuthenticationManager
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));

			// JWT Token
			String jwt = jwtUtil.generateToken(user.getUserEmail());

			return ResponseEntity.ok(new LoginResponse(user, jwt, null, "Login Successful"));

		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(new LoginResponse(null, null, "Invalid credentials", null));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(new LoginResponse(null, null, "Server error during login", null));
		}
	}

	@GetMapping("/by-email")
	public ResponseEntity<?> getUserByEmail(@RequestParam String email) {
		User user = authService.getUserDetailsByEmail(email);
		if (user != null) {
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + email);
		}
	}

}
