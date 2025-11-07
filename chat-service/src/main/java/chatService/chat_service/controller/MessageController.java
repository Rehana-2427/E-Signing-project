package chatService.chat_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import chatService.chat_service.dto.MessageDTO;
import chatService.chat_service.modal.Message;
import chatService.chat_service.service.MessageService;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

	@Autowired
	private MessageService messageService;

	@PostMapping("/save-message")
	public ResponseEntity<Message> sendMessage(@RequestBody MessageDTO messageDTO) {
		Message message = messageService.saveMessage(messageDTO);
		return ResponseEntity.ok(message);
	}

	@GetMapping("/fetch-messages")
	public ResponseEntity<List<Message>> getMessages(@RequestParam int documentId, @RequestParam String userEmail) {
		List<Message> messages = messageService.getMessagesForDocument(documentId, userEmail);
		return ResponseEntity.ok(messages);
	}

}
