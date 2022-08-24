package in.shivamagarwal.chatlogserver.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.shivamagarwal.chatlogserver.entity.CreateMessageResponse;
import in.shivamagarwal.chatlogserver.entity.Message;
import in.shivamagarwal.chatlogserver.exceptions.ImproperParameterException;
import in.shivamagarwal.chatlogserver.exceptions.ItemNotFoundException;
import in.shivamagarwal.chatlogserver.repository.MessageRepository;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageRepository messagerepo;

	@Override
	public List<Message> readMessages(String username,Long start,Integer limit) {
		List<Message> s = messagerepo.findByUsername(username);
		if(s.size()==0) {
			throw new ItemNotFoundException("There are no chatlogs for the user " + username);
		}
		Collections.sort(s);
		if(start==null) {
			start=1l;
		}
		if(limit==null) {
			limit=10;
		}
		long a = s.size();
		if(start > a) {
			throw new ImproperParameterException("The start position exceeds the number of messages.The number of messages are " + a +", So start position should not be more than that.");		
		}
		long c = a - start + 1;
		ArrayList<Message> res = new ArrayList<Message>();
		if (c<limit) {
			for(long i=start-1;i < start-1+c;i++) {
				res.add(s.get((int) i));
			}
		}
		else if(c>=limit) {
			for(long i=start-1;i < start-1+limit;i++) {
				res.add(s.get((int) i));
			}
		}
		return res;
	}

	@Override
	public CreateMessageResponse saveMessageForUser(String username, Message message) {
		message.setUsername(username);
		CreateMessageResponse mes = new CreateMessageResponse();
		Message savemessage=messagerepo.save(message);
		mes.setMessageID(savemessage.getId());
		return mes;
		
	}

	@Override
	@Transactional
	public List<Message> deleteMessagesForUser(String username) {
		checkUserExists(username);
		return messagerepo.deleteByUsername(username);
		
	}
	
	
	
	@Override
	@Transactional
	public String deleteMessage(String username, Long id) {
		checkUserExists(username);
		Optional<Message> message = messagerepo.findByIdAndUsername(id,username);
		if(message.isPresent()) {
			messagerepo.delete(message.get());
			return "The message has been deleted";
		}
		else {
			throw new ItemNotFoundException("The message for the messageid does not exist");
		}
	}

	@Override
	public void checkUserExists(String username) {
		List<Message> s = messagerepo.findByUsername(username);
		if(s.size()==0) {
			throw new ItemNotFoundException("There are no chatlogs for the user " + username);
		}
		
	}
 
}
