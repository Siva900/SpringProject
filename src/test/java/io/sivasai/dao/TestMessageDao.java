package io.sivasai.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import io.sivasai.model.MessageClass;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestMessageDao {

	@Autowired
    MessageDao messageDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Test
	public void TestInsert() throws SQLException {
		MessageClass newMsg = new MessageClass("5","919847548925","This is Insert test","2022-05-25 09:13:00","2022-05-24 19:13:00");
		
		assertThat(messageDao.InsertMessage(newMsg.getClient_id(), newMsg.getMessage_body(), newMsg.getRecipient_number(), newMsg.getScheduled_time()))
		.isEqualTo(true);
		
	}

	
	@Test
	public void TestGetMsgId() {
		
		MessageClass newMsg = new MessageClass(1,"1","919542684424","hi!! this is siva sai" ,"2022-05-24 23:12:00", "2022-05-21 19:16:23",1);
		System.out.println(messageDao.getMessageById(newMsg.getID())+" \n"+newMsg.toString());
		
		assertThat(messageDao.getMessageById(newMsg.getID()).toString()).isEqualTo(newMsg.toString());
		
	}
	
	@Test
	public void TestGetMsgIdInvalid() {
		
		int num = 0;
		
		assertThat(messageDao.getMessageById(num)).isEqualTo(null);
		
	}
	
	@Test
	public void TestMsgPoll() throws SQLException {
		
		String sqlQuery1 = "SELECT * FROM Messages WHERE sent_status=0 AND scheduled_time>CURRENT_TIMESTAMP AND TIMEDIFF(scheduled_time,CURRENT_TIMESTAMP)<'00:02:30'";
		
		String sqlQuery2 = "SELECT * FROM Messages WHERE sent_status=0 AND scheduled_time<CURRENT_TIMESTAMP AND TIMEDIFF(CURRENT_TIMESTAMP,scheduled_time)<'00:02:30'";
		
		String sqlQuery3 = sqlQuery1 + " UNION "+sqlQuery2;
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery3);
		
		List<MessageClass> actualMsgList = new ArrayList<MessageClass>();
		
		for(Map<String, Object> row:rows){
			MessageClass msg = new MessageClass();
			
			msg.setID((int)row.get("ID"));
			msg.setClient_id((String)row.get("client_id").toString());
			msg.setArrival_time((String)row.get("arrival_time").toString());
			msg.setScheduled_time((String)row.get("scheduled_time").toString());
			msg.setMessage_body((String)row.get("message_body"));
			msg.setRecipient_number((String)row.get("recipient_number"));
			msg.setSent_status((int)row.get("sent_status"));
			
			actualMsgList.add(msg);
		}
		
		assertThat(messageDao.getUnsentMessages().size()).isEqualTo(actualMsgList.size());
	}
	
	@Test
	public void TestUpdateDB() {
		MessageClass newMsg = new MessageClass(1,"1","919542684424","hi!! this is siva sai" ,"2022-05-24 23:12:00", "2022-05-21 19:16:23",1);
		
		assertThat(messageDao.UpdateDatabase(HttpStatus.ACCEPTED.value(), 1, newMsg.getID())).isEqualTo(true);
		
	}
	
	@Test
	public void TestUpdateDBInvalid() {
		
		assertThat(messageDao.UpdateDatabase(0, 1, 1)).isEqualTo(false);	
	}
}
