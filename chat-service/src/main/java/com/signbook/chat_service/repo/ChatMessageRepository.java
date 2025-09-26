package com.signbook.chat_service.repo;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.signbook.chat_service.modal.ChatMessage;

public interface ChatMessageRepository extends MongoRepository<ChatMessage, String>{

}
