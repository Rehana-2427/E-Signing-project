package com.signbook.chat_service.serviceIMpl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.signbook.chat_service.modal.ChatMessage;
import com.signbook.chat_service.repo.ChatMessageRepository;
import com.signbook.chat_serviceInterface.ChatService;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private ChatMessageRepository chatMessageRepository;

	@Override
	public ChatMessage saveMessage(ChatMessage message) {
		// TODO Auto-generated method stub
		message.setTimestamp(LocalDateTime.now());
		return chatMessageRepository.save(message);
	}

}
