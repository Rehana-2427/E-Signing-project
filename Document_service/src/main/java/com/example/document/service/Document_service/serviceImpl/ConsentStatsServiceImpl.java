package com.example.document.service.Document_service.serviceImpl;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.document.service.Document_service.dto.ConsentStatsDto;
import com.example.document.service.Document_service.repo.DocumentRepository;
import com.example.document.service.Document_service.repo.SignerRepository;
import com.example.document.service.Document_service.service.ConsentStatsService;

@Service
public class ConsentStatsServiceImpl implements ConsentStatsService {

	@Autowired
	private DocumentRepository documentRepository;
	@Autowired
	private SignerRepository signerRepository;

	@Override
	public ConsentStatsDto getConsentStats(String userEmail) {
		LocalDate today = LocalDate.now();

		Long totalSent = documentRepository.countBySenderEmailAndDraftFalse(userEmail);
		Long drafts = documentRepository.countBySenderEmailAndDraftTrue(userEmail);
		Long active = documentRepository.countActiveConsents(userEmail, today);
		Long expired = documentRepository.countExpiredConsents(userEmail, today);

		Long totalReceived = signerRepository.countTotalReceived(userEmail);
		Long completed = signerRepository.countCompleted(userEmail);
		Long pending = signerRepository.countPending(userEmail);

		ConsentStatsDto statsDto = new ConsentStatsDto();
		statsDto.setTotalSentConsents(totalSent);
		statsDto.setDraftConsents(drafts);
		statsDto.setActiveConsents(active);
		statsDto.setExpiredConsents(expired);
		statsDto.setTotalReceivedConsents(totalReceived);
		statsDto.setCompletedConsents(completed);
		statsDto.setPendingConsents(pending);

		return statsDto;
	}

	@Override
	public List<ConsentStatsDto> getMonthlyStats(String userEmail) {
	    List<Object[]> sentStats = documentRepository.getSentConsentStatsByMonth(userEmail);
	    List<Object[]> receivedStats = signerRepository.getReceivedConsentStatsByMonth(userEmail);

	    Map<Integer, ConsentStatsDto> statsMap = new HashMap<>();

	    for (Object[] row : sentStats) {
	        Integer month = (Integer) row[0];
	        Long count = (Long) row[1];
	        String monthName = Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	        statsMap.putIfAbsent(month, new ConsentStatsDto(monthName, 0L, 0L));
	        statsMap.get(month).setTotalSentConsents(count);
	    }

	    for (Object[] row : receivedStats) {
	        Integer month = (Integer) row[0];
	        Long count = (Long) row[1];
	        String monthName = Month.of(month).getDisplayName(TextStyle.SHORT, Locale.ENGLISH);

	        statsMap.putIfAbsent(month, new ConsentStatsDto(monthName, 0L, 0L));
	        statsMap.get(month).setTotalReceivedConsents(count);
	    }

	    return statsMap.entrySet().stream()
	            .sorted(Map.Entry.comparingByKey())
	            .map(Map.Entry::getValue)
	            .collect(Collectors.toList());
	}

}
