package com.example.adminservice.adminservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.adminservice.adminservice.modal.CreditSettings;
import com.example.adminservice.adminservice.repo.CreditSettingsRepository;
import com.example.adminservice.adminservice.service.CreditSettingsService;

@Service
public class CreditSettingsServiceImpl implements CreditSettingsService {

	@Autowired
	private CreditSettingsRepository creditSettingsRepository;

	@Override
	public CreditSettings updateSettings(int docCost, int signCost) {
		CreditSettings settings = new CreditSettings();
		settings.setDocCost(docCost);
		settings.setSignCost(signCost);
		settings.setUpdatedAt(LocalDate.now());
		return creditSettingsRepository.save(settings);
	}

	@Override
//	@Cacheable("creditHistory")
	public List<CreditSettings> getAllSettings() {
		// TODO Auto-generated method stub
		System.out.println("Fetching from DB...");
		return creditSettingsRepository.findAllByOrderByUpdatedAtDescIdDesc();

	}
//
//	@CacheEvict(value = "creditHistory", allEntries = true)
//	public void updateCreditSettings(CreditSettings newSetting) {
//		creditSettingsRepository.save(newSetting);
//	}

	@Override
	public CreditSettings getCurrentSettings() {
		// TODO Auto-generated method stub
		return creditSettingsRepository.findTopByOrderByUpdatedAtDescIdDesc();
	}

}
