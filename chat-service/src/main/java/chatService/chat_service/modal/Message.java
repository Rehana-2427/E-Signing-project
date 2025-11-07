package chatService.chat_service.modal;

import java.time.LocalDateTime; // Import LocalDateTime
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "messages")
public class Message {

	@Id
	private String id;

	private int documentId;
	private String sender;
	private String content;
	private List<String> recipients; // List of recipient emails
	private LocalDateTime timestamp; // Use LocalDateTime for both date and time
	private String fileUrl;

	public Message(int documentId, String sender, String content, List<String> recipients, LocalDateTime timestamp,
			String fileUrl) {
		super();
		this.documentId = documentId;
		this.sender = sender;
		this.content = content;
		this.recipients = recipients;
		this.timestamp = timestamp;
		this.fileUrl = fileUrl;
	}


	// Getters and Setters
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getDocumentId() {
		return documentId;
	}

	public void setDocumentId(int documentId) {
		this.documentId = documentId;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
