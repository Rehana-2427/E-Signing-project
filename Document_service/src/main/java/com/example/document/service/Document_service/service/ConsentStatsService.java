package com.example.document.service.Document_service.service;

import java.util.List;

import com.example.document.service.Document_service.dto.ConsentStatsDto;

public interface ConsentStatsService {

	ConsentStatsDto getConsentStats(String userEmail);

	List<ConsentStatsDto> getMonthlyStats(String userEmail);

}
