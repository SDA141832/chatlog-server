package in.shivamagarwal.chatlogserver.service;

import java.util.List;

import in.shivamagarwal.chatlogserver.entity.CreateMessageResponse;
import in.shivamagarwal.chatlogserver.entity.Message;

public interface MessageService {
	
	List<Message> readMessages(String username,Long start,Integer limit);
	
	CreateMessageResponse saveMessageForUser(String username,Message message);
	
	List<Message> deleteMessagesForUser(String username);
	
	String deleteMessage(String username,Long id);
	
	void checkUserExists(String username);
		
}
