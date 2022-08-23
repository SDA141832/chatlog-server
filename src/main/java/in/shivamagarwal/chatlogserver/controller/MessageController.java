package in.shivamagarwal.chatlogserver.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.shivamagarwal.chatlogserver.entity.CreateMessageResponse;
import in.shivamagarwal.chatlogserver.entity.Message;
import in.shivamagarwal.chatlogserver.service.MessageService;

@RestController
public class MessageController {
	
	@Autowired
	private MessageService messageService;
	
	@GetMapping("/chatlogs/{user}")
	public Map<String,Object> getMessages(@PathVariable("user") String username, @RequestParam(required = false) Long start, @RequestParam(required=false) Integer limit) throws Exception {
		List<Message> messages;
		try {
			messages = messageService.readMessages(username,start,limit);
			String encode = Base64.getEncoder().encodeToString(messages.toString().getBytes());
			Map<String,Object> result = new HashMap<>();
			result.put("data",encode);
			result.put("Json Response",messages);
			return result;
		} catch (Exception e) {
			
			e.printStackTrace();
			throw e;
		}
		
		//return messageService.readMessages(username,start,limit);
	}
	
	@ResponseStatus(value = HttpStatus.CREATED)
	@PostMapping("/chatlogs/{user}")
	public CreateMessageResponse createMessage(@PathVariable("user") String username, @RequestBody Map<String,Object> requestMap) throws JsonMappingException, JsonProcessingException, IOException  {
		
		String encodedString = (String) requestMap.get("data");

		byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
		String decodedString = new String(decodedBytes);
		
		ObjectMapper mapper = new ObjectMapper();
		Message message = mapper.readValue(decodedString, Message.class); 
		return messageService.saveMessageForUser(username, message);
	}
	
	@DeleteMapping("/chatlogs/{user}")
	public List<Message> deleteMessagesForUser(@PathVariable("user") String username){
		return messageService.deleteUser(username);
	}
		
	@DeleteMapping("/chatlogs/{user}/{msgid}")
	public String deleteMessageById(@PathVariable("user") String username, @PathVariable("msgid") Long id) {
		return messageService.deleteMessage(username, id);
	}

}


