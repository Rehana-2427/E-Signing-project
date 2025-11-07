package chatService.chat_service.repo;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import chatService.chat_service.modal.Message;

public interface MessageRepository extends MongoRepository<Message, String> {

	List<Message> findByDocumentIdAndSenderOrDocumentIdAndRecipientsContaining(int documentId, String sender,
			int documentId2, String recipient);

}
