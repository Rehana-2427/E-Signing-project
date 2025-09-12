package com.example.adminservice.adminservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.adminservice.adminservice.modal.UserCredits;

@Repository
public interface UserCreditsRepository extends JpaRepository<UserCredits, Long> {
    UserCredits findByUserEmail(String userEmail); 
}
