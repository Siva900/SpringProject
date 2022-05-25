package io.sivasai.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.sivasai.dao.MessageDao;
import io.sivasai.model.MessageClass;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMessageService {
	
	@Autowired
	private MessageService messageService;
	
	@MockBean
	MessageDao messageDao;

	@Test
	public void TestInsert() throws SQLException {
		MessageClass newMsg = new MessageClass("5","919847548925","This is Insert test","2022-05-25 09:13:00","2022-05-24 19:13:00");
		
		when(messageDao.InsertMessage(newMsg.getClient_id(), newMsg.getMessage_body(), newMsg.getRecipient_number(), newMsg.getScheduled_time()))
		.thenReturn(true);
		assertThat(messageService.InsertMessage(newMsg.getClient_id(), newMsg.getMessage_body(), newMsg.getRecipient_number(), newMsg.getScheduled_time()))
		.isEqualTo(true);
		
	}
	
	@Test
	public void TestInsertInvalid() throws SQLException {
		
		String invalid = "invalid";
		MessageClass newMsg = new MessageClass(invalid,invalid,invalid,invalid,invalid);
		
		when(messageDao.InsertMessage(newMsg.getClient_id(), newMsg.getMessage_body(), newMsg.getRecipient_number(), newMsg.getScheduled_time()))
		.thenReturn(null);
		assertThat(messageService.InsertMessage(newMsg.getClient_id(), newMsg.getMessage_body(), newMsg.getRecipient_number(), newMsg.getScheduled_time()))
		.isEqualTo(null);
		
	}
	
	@Test
	public void TestGetMsgId() {
		
		MessageClass newMsg = new MessageClass("5","919847548925","This is Insert test","2022-05-25 09:13:00","2022-05-24 19:13:00");
		
		when(messageDao.getMessageById(newMsg.getID())).thenReturn(newMsg);
		assertThat(messageService.getMessageById(newMsg.getID()).toString()).isEqualTo(newMsg.toString());
		
	}
	
	@Test
	public void TestGetMsgIdInvalid() {
		
		MessageClass newMsg = new MessageClass();
		
		when(messageDao.getMessageById(newMsg.getID())).thenReturn(null);
		assertThat(messageService.getMessageById(newMsg.getID())).isEqualTo(null);
		
	}
	
	@Test
	public void TestMsgPoll() {
		
		List<MessageClass> messagesList = Collections.emptyList();
		
		when(messageDao.getAllMessages()).thenReturn(messagesList);
		
		List<MessageClass> actualMsgList = messageService.getAllMessages();
		assertThat(messageService.getAllMessages().size()).isEqualTo(actualMsgList.size());	
	}
	
	@Test
	public void TestUpdateDB() {
		MessageClass newMsg = new MessageClass("5","919847548925","This is Insert test","2022-05-25 09:13:00","2022-05-24 19:13:00");
		
		when(messageDao.UpdateDatabase(HttpStatus.ACCEPTED.value(), 1, newMsg.getID())).thenReturn(true);
		assertThat(messageService.UpdateDatabase(HttpStatus.ACCEPTED.value(), 1, newMsg.getID())).isEqualTo(true);
		
	}
	
	@Test
	public void TestUpdateDBInvalid() {
		MessageClass newMsg = new MessageClass("5","919847548925","This is Insert test","2022-05-25 09:13:00","2022-05-24 19:13:00");
		
		when(messageDao.UpdateDatabase(0, 1, newMsg.getID())).thenReturn(false);
		assertThat(messageService.UpdateDatabase(0, 1, newMsg.getID())).isEqualTo(false);
		
	}

}
