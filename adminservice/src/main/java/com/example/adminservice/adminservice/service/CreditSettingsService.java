package com.example.adminservice.adminservice.service;

import java.util.List;

import com.example.adminservice.adminservice.modal.CreditSettings;

public interface CreditSettingsService {

	CreditSettings updateSettings(int docCost, int signCost);

	List<CreditSettings> getAllSettings();

	CreditSettings getCurrentSettings();

}
