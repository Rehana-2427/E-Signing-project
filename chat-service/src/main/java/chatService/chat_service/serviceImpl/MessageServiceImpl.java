package chatService.chat_service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chatService.chat_service.dto.MessageDTO;
import chatService.chat_service.modal.Message;
import chatService.chat_service.repo.MessageRepository;
import chatService.chat_service.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Override
	public Message saveMessage(MessageDTO messageDTO) {
		Message message = new Message(messageDTO.getDocumentId(), messageDTO.getSender(), messageDTO.getContent(),
				messageDTO.getRecipients(), messageDTO.getTimestamp(), messageDTO.getFileUrl());
		return messageRepository.save(message);
	}

	@Override
	public List<Message> getMessagesForDocument(int documentId, String userEmail) {
		// TODO Auto-generated method stub
		 return messageRepository.findByDocumentIdAndSenderOrDocumentIdAndRecipientsContaining(documentId, userEmail, documentId, userEmail);
	}

}
