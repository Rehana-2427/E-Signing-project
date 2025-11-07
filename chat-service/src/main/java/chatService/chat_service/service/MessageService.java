package chatService.chat_service.service;

import java.util.List;

import chatService.chat_service.dto.MessageDTO;
import chatService.chat_service.modal.Message;

public interface MessageService {

	Message saveMessage(MessageDTO messageDTO);

	List<Message> getMessagesForDocument(int documentId, String sender);

}
