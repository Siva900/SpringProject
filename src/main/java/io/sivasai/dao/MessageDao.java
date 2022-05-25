package io.sivasai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import io.sivasai.model.MessageClass;

@Repository
public class MessageDao extends JdbcDaoSupport{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	ResponseDao responseDao;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	
	
	public Boolean InsertMessage(String client_id,String message_body, String recipient_number, String scheduled_time) throws SQLException{
		
		Boolean flag = false;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();  
		
		System.out.println("Current time "+dtf.format(now));
		   
		String arrival_time = dtf.format(now);
		
		String sqlQuery = "INSERT INTO Messages(client_id,recipient_number,message_body,scheduled_time,arrival_time) values (?,?,?,?,?)";
		
		int ret = jdbcTemplate.update(sqlQuery,client_id,recipient_number,message_body,scheduled_time,arrival_time);
		
		if(ret!=0)flag = true;
		
		return flag;
	}

	public List<MessageClass> getAllMessages() {
		
		String sqlQuery = "SELECT * FROM Messages";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sqlQuery);
		List<MessageClass> return_list = new ArrayList<MessageClass>();
		
		for(Map<String, Object> row:rows){
			MessageClass msg = new MessageClass();
			msg.setID((int)row.get("ID"));
			msg.setClient_id(row.get("client_id").toString());
			msg.setArrival_time(row.get("arrival_time").toString());
			msg.setScheduled_time(row.get("scheduled_time").toString());
			msg.setMessage_body((String)row.get("message_body"));
			msg.setRecipient_number((String)row.get("recipient_number"));
			
			return_list.add(msg);
		}
		
		return return_list;
	}

	public MessageClass getMessageById(int Id) {
		
		String sqlQuery = "SELECT * FROM Messages WHERE ID = ?";
		
		return getJdbcTemplate().query(sqlQuery, new ResultSetExtractor<MessageClass>(){

			@Override
			public MessageClass extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					MessageClass msg = new MessageClass();
					msg.setID(rs.getInt("ID"));
					msg.setClient_id(rs.getString("client_id"));
					msg.setArrival_time(rs.getString("arrival_time"));
					msg.setScheduled_time(rs.getString("scheduled_time"));
					msg.setMessage_body(rs.getString("message_body"));
					msg.setRecipient_number(rs.getString("recipient_number"));
					msg.setSent_status(rs.getInt("sent_status"));
					return msg;
				}
				else
					return null;
			}
		}, new Object[]{Id});
	}

	public List<MessageClass> getUnsentMessages() throws SQLException{
		
		String sqlQuery1 = "SELECT * FROM Messages WHERE sent_status=0 AND scheduled_time>CURRENT_TIMESTAMP AND TIMEDIFF(scheduled_time,CURRENT_TIMESTAMP)<'00:02:30'";
		
		String sqlQuery2 = "SELECT * FROM Messages WHERE sent_status=0 AND scheduled_time<CURRENT_TIMESTAMP AND TIMEDIFF(CURRENT_TIMESTAMP,scheduled_time)<'00:02:30'";
		
		String sqlQuery3 = sqlQuery1 + " UNION "+sqlQuery2;
		
		List<Map<String, Object>> rows = jdbcTemplate.queryForList(sqlQuery3);
		List<MessageClass> return_list = new ArrayList<MessageClass>();
		
		for(Map<String, Object> row:rows){
			MessageClass msg = new MessageClass();
			
			msg.setID((int)row.get("ID"));
			msg.setClient_id(row.get("client_id").toString());
			msg.setArrival_time(row.get("arrival_time").toString());
			msg.setScheduled_time(row.get("scheduled_time").toString());
			msg.setMessage_body((String)row.get("message_body"));
			msg.setRecipient_number((String)row.get("recipient_number"));
			msg.setSent_status((int)row.get("sent_status"));
			
			//System.out.println("here msg sched "+row.get("scheduled_time").toString());
			
			return_list.add(msg);
			
		}
		
		
		return return_list;
	}
	
	public Boolean UpdateDatabase(int resp_code, int sent_status,int id) {
		Boolean flag = false;
		
		//get response msg
		String resp_msg = responseDao.return_response_msg(resp_code);
		
		String sqlQuery = "UPDATE Messages SET sent_status=?, response_code=?, response_msg=? WHERE ID=?";
		
		try {
			if(resp_msg == null)
				return flag;
			
			int ret = jdbcTemplate.update(sqlQuery,sent_status,resp_code,resp_msg,id);
			
			if(ret!=0)flag = true;
		}
		catch(EmptyResultDataAccessException e) {
			return false;
		}
		
		
		return flag;
	}
	
	public Boolean UpdateTriesCount(int id) {
		Boolean flag = false;
		//get tries count
		String sqlQuery = "SELECT ID,tries_count FROM Messages WHERE ID=?";
		
		int ret = getJdbcTemplate().queryForObject(sqlQuery, new RowMapper<MessageClass>(){
			@Override
			public MessageClass mapRow(ResultSet rs, int rwNumber) throws SQLException {
				MessageClass msg = new MessageClass();
				msg.setID(rs.getInt("ID"));
				msg.setTries_count(rs.getInt("tries_count"));
				return msg;
			}
		}, new Object[]{id}).getTries_count();
		
		ret+=1;
		
		//set tries count
		sqlQuery = "UPDATE Messages SET tries_count=? WHERE id=?";
		
		int fret = jdbcTemplate.update(sqlQuery,ret,id);
		
		if(fret!=0)flag = true;
		
		return flag;
	}
	
}
