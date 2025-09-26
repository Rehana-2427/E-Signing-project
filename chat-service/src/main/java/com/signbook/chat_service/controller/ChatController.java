package com.signbook.chat_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.signbook.chat_service.modal.ChatMessage;
import com.signbook.chat_serviceInterface.ChatService;

@RequestMapping("api/chat/")
@RestController
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping("/send")
	public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessage message) {
		ChatMessage savedMessage = chatService.saveMessage(message);
		return ResponseEntity.ok(savedMessage);
	}

}
