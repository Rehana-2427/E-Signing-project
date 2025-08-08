package com.example.adminservice.adminservice.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.adminservice.adminservice.modal.CreditSettings;

public interface CreditSettingsRepository extends JpaRepository<CreditSettings, Long>{

	List<CreditSettings> findAllByOrderByUpdatedAtDescIdDesc();

	CreditSettings findTopByOrderByUpdatedAtDescIdDesc();

}
