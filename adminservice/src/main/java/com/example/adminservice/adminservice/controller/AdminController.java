package com.example.adminservice.adminservice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.adminservice.adminservice.dto.AssignCreditsRequest;
import com.example.adminservice.adminservice.dto.CreditRequestMessage;
import com.example.adminservice.adminservice.dto.UserCreditsDTO;
import com.example.adminservice.adminservice.dto.UserDTO;
import com.example.adminservice.adminservice.modal.CreditRequest;
import com.example.adminservice.adminservice.modal.CreditTransaction;
import com.example.adminservice.adminservice.modal.UserCredits;
import com.example.adminservice.adminservice.service.CreditRequestService;
import com.example.adminservice.adminservice.service.UserCreditsService;
import com.example.adminservice.adminservice.service.UserSyncService;

@RestController
@RequestMapping("/api/userCreditsList")
public class AdminController {


	@Autowired
	private UserSyncService userSyncService;

	@Autowired
	private CreditRequestService creditRequestService;

	@Autowired
	private UserCreditsService userCreditsService;

	@GetMapping("/sync-users")
	public ResponseEntity<String> syncUsers() {
		userSyncService.syncUsers();
		return ResponseEntity.ok("Users synced successfully");
	}

	// In AdminController.java

	@PostMapping("/sync-user")
	public ResponseEntity<String> createUserCredit(@RequestBody UserDTO userCreditsDTO) {
		String result = userCreditsService.createUserCredits(userCreditsDTO);
		return ResponseEntity.ok(result);
	}

	@GetMapping("/allUsersCredits")
	public ResponseEntity<List<UserCredits>> getAllSyncedUsers() {
		return ResponseEntity.ok(userSyncService.getAllUserCredits());
	}

	@GetMapping("/userCredits")
	public UserCreditsDTO getCreditsByUserEmail(@RequestParam String email) {
		return userSyncService.getUserCreditsByEmail(email);
	}

	@PostMapping("/request-credits")
	public ResponseEntity<?> requestCredits(@RequestBody CreditRequestMessage message) {
		creditRequestService.saveRequest(message);
		return ResponseEntity.ok("Credit request submitted");
	}

	@GetMapping("/unseen-requests")
	public List<CreditRequest> getUnseenRequests() {
		return creditRequestService.getUnseenRequests();
	}

	@PutMapping("/mark-seen/{id}")
	public ResponseEntity<String> markRequestAsSeen(@PathVariable Long id) {
		creditRequestService.markAsSeen(id);
		return ResponseEntity.ok("Marked as seen");
	}

	@PutMapping("/assign-credits")
	public ResponseEntity<String> assignCredits(@RequestBody AssignCreditsRequest request) {
		System.out.println(request.getCreditsToAssign() + " ---" + request.getUserEmail());
		userCreditsService.assignCreditsToUser(request.getUserEmail(), request.getCreditsToAssign());
		return ResponseEntity.ok("Credits assigned successfully");
	}

	@PutMapping("/updateUserCredits")
	public ResponseEntity<UserCredits> updateUserCredits(@RequestParam String userEmail,
			@RequestBody Map<String, Integer> updateData) {

		if (!updateData.containsKey("balanceCredit") || !updateData.containsKey("usedCredit")) {
			return ResponseEntity.badRequest().build();
		}

		int balanceCredit = updateData.get("balanceCredit");
		int usedCredit = updateData.get("usedCredit");

		UserCredits updated = userCreditsService.updateUserCredits(userEmail, balanceCredit, usedCredit);
		return ResponseEntity.ok(updated);
	}

	@PostMapping("/creditTransactions")
	public ResponseEntity<CreditTransaction> saveCreditTransaction(@RequestBody CreditTransaction transaction) {
		CreditTransaction saved = userCreditsService.saveCreditTransaction(transaction);
		return ResponseEntity.ok(saved);
	}

	@GetMapping("/getCredtTransactionsByUser")
	public ResponseEntity<List<CreditTransaction>> getCreditTransactions(@RequestParam String userEmail) {
		List<CreditTransaction> transactions = userCreditsService.getCreditTransactionsByUser(userEmail);
		return ResponseEntity.ok(transactions);
	}
}
