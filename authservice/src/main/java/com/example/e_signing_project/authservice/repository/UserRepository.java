package com.example.e_signing_project.authservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.e_signing_project.authservice.dto.UserDTO;
import com.example.e_signing_project.authservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	@Query("select u from User u where u.userEmail=?1")
	User findByUserEmail(String email);

	@Query("SELECT new com.example.e_signing_project.authservice.dto.UserDTO(u.userName, u.userEmail) FROM User u")
	List<UserDTO> findAllUsersWithNamesAndEmails();

}
