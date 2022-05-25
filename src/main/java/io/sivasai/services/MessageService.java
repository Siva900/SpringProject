package io.sivasai.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.sivasai.dao.MessageDao;
import io.sivasai.model.MessageClass;

@Service
public class MessageService{
	
	@Autowired
	MessageDao messageDao;

	public Boolean InsertMessage(String client_id, String message_body, String recipient_number,String scheduled_time) throws SQLException {
		Boolean flag = messageDao.InsertMessage(client_id, message_body, recipient_number, scheduled_time);
		return flag;
	}

	public List<MessageClass> getAllMessages() {
		
		List<MessageClass> msgs = new ArrayList<MessageClass>();
		msgs = messageDao.getAllMessages();
		
		System.out.println("Message service print: ");
		for (MessageClass msg : msgs) {
			System.out.println(msg.toString());
		}
		return msgs;
	}

	public MessageClass getMessageById(int Id) {
		MessageClass msg = new MessageClass();
		msg = messageDao.getMessageById(Id);
		return msg;
	}

	public List<MessageClass> PollMessages() throws SQLException {
		
		List<MessageClass> msgs = new ArrayList<MessageClass>();
		msgs = messageDao.getUnsentMessages();
		
		return msgs;
	}

	public Boolean UpdateDatabase(int resp_code, int sent_status,int id) {
		Boolean flag = messageDao.UpdateDatabase(resp_code, sent_status,id);
		
		return flag;
	}

	public Boolean UpdateTriesCount(int id) {
		Boolean flag = messageDao.UpdateTriesCount(id);
		
		return flag;
	}
}