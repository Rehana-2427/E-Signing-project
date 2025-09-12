package com.example.document.service.Document_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.document.service.Document_service.dto.ConsentStatsDto;
import com.example.document.service.Document_service.service.ConsentStatsService;

@RestController
@RequestMapping("/api/consent-stats")
public class ConsentStatsController {

	@Autowired
	private ConsentStatsService consentStatsService;

	@GetMapping("/consentstats")
	public ResponseEntity<ConsentStatsDto> getStats(@RequestParam String userEmail) {
		ConsentStatsDto stats = consentStatsService.getConsentStats(userEmail);
		return ResponseEntity.ok(stats);
	}

	@GetMapping("/monthlystats")
	public ResponseEntity<List<ConsentStatsDto>> getMonthlyStats(@RequestParam String userEmail) {
		return ResponseEntity.ok(consentStatsService.getMonthlyStats(userEmail));
	}

}
