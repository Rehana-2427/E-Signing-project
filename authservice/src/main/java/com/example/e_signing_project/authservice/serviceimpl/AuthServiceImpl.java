package com.example.e_signing_project.authservice.serviceimpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.e_signing_project.authservice.config.JwtUtil;
import com.example.e_signing_project.authservice.dto.RegisterRequest;
import com.example.e_signing_project.authservice.dto.UserDTO;
import com.example.e_signing_project.authservice.exception.EmailAlreadyExistsException;
import com.example.e_signing_project.authservice.fiegnclient.AdminServiceClient;
import com.example.e_signing_project.authservice.model.User;
import com.example.e_signing_project.authservice.repository.UserRepository;
import com.example.e_signing_project.authservice.service.AuthService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

	@Autowired
	@Lazy
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private AdminServiceClient adminServiceClient;

	@Override
	public String registerUser(RegisterRequest request) {
		String email = request.getUserEmail();
		User existingUser = userRepository.findByUserEmail(email);
		if (existingUser != null) {
			throw new EmailAlreadyExistsException("Email already exists");
		}
		String plainTextPassword = request.getPassword();
		String hashedPassword = bCryptPasswordEncoder.encode(plainTextPassword);
		User user = new User();
		user.setUserEmail(request.getUserEmail());
		user.setUserName(request.getUserName());
		user.setPassword(hashedPassword);
		user.setEmailVerified(true);
		userRepository.save(user);
		UserDTO dto = new UserDTO();
		dto.setUserEmail(email);
		dto.setUserName(request.getUserName());
		try {
			adminServiceClient.createUserCredits(dto);
		} catch (Exception ex) {
			// Log and handle error — maybe retry later or send to a dead-letter queue
			System.out.println("Failed to sync with AdminService: " + ex.getMessage());
		}
		return jwtUtil.generateToken(user.getUserEmail());
	}

	@Override
	public User getUserByEmail(String userEmail) {
		return userRepository.findByUserEmail(userEmail);
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User u = userRepository.findByUserEmail(email);
		if (u == null)
			throw new UsernameNotFoundException("User not found");
		return org.springframework.security.core.userdetails.User.withUsername(u.getUserEmail())
				.password(u.getPassword()).authorities(new ArrayList<>()).build();
	}

	@Override
	public User getUserDetailsByEmail(String email) {
		return userRepository.findByUserEmail(email);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepository.findAllUsersWithNamesAndEmails();
	}

	@Override
	public String googleLogin(String idTokenString) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
					new JacksonFactory()).setAudience(Collections.singletonList("13721221316-vepimpc72pa1r5f443thq7vqo7rl5b0o.apps.googleusercontent.com")).build();

			GoogleIdToken idToken = verifier.verify(idTokenString);

			if (idToken != null) {
				GoogleIdToken.Payload payload = idToken.getPayload();

				String email = payload.getEmail();
				String name = (String) payload.get("name");
				boolean emailVerified = Boolean.TRUE.equals(payload.getEmailVerified());

				// ✅ Check if user exists
				User user = userRepository.findByUserEmail(email);

				if (user == null) {
					// 🛑 DO NOT create user yet
					// Instead, generate token with payload info
					return jwtUtil.generateToken(email); // JWT still based on email
				}

				// ✅ If user exists, return normal JWT
				return jwtUtil.generateToken(user.getUserEmail());
			} else {
				throw new RuntimeException("Invalid ID token");
			}
		} catch (Exception e) {
			throw new RuntimeException("Google authentication failed", e);
		}
	}

}
