package com.example.e_signing_project.authservice.serviceimpl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.e_signing_project.authservice.config.JwtUtil;
import com.example.e_signing_project.authservice.dto.RegisterRequest;
import com.example.e_signing_project.authservice.exception.EmailAlreadyExistsException;
import com.example.e_signing_project.authservice.model.User;
import com.example.e_signing_project.authservice.repository.UserRepository;
import com.example.e_signing_project.authservice.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

	@Autowired
	@Lazy
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserRepository userRepository;

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
		return JwtUtil.generateToken(user.getUserEmail());

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

}
