package com.example.adminservice.adminservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminservice.adminservice.dto.CreditSettingsRequest;
import com.example.adminservice.adminservice.modal.CreditSettings;
import com.example.adminservice.adminservice.service.CreditSettingsService;

@RestController
@RequestMapping("/api/admin")
public class CreditSettingsController {

	@Autowired
	private CreditSettingsService creditSettingsService;

	@PostMapping("/saveCredits")
	public CreditSettings updateSettings(@RequestBody CreditSettingsRequest request) {
		return creditSettingsService.updateSettings(request.getDocCost(), request.getSignCost());
	}

	@GetMapping("/creditHistory")
	public List<CreditSettings> getCreditHistory() {
		return creditSettingsService.getAllSettings();
	}

	@GetMapping("/currentCredits")
	public CreditSettings getSettings() {
		return creditSettingsService.getCurrentSettings();
	}

	

}
