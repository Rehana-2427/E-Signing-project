package com.signbook.chat_serviceInterface;

import com.signbook.chat_service.modal.ChatMessage;

public interface ChatService {

	ChatMessage saveMessage(ChatMessage message);

}
