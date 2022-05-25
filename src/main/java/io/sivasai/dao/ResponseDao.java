package io.sivasai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import io.sivasai.model.ResponseClass;

@Repository
public class ResponseDao extends JdbcDaoSupport{
	
	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}
	

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public String return_response_msg(int code) {
		
		String sqlQuery = "SELECT * FROM response WHERE status_code=?";
		
		ResponseClass ret = jdbcTemplate.query(sqlQuery, new ResultSetExtractor<ResponseClass>(){

			@Override
			public ResponseClass extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					ResponseClass resp = new ResponseClass();
					resp.setID(rs.getInt("ID"));
					resp.setResponse_code(rs.getInt("status_code"));
					resp.setResponse_msg(rs.getString("status_msg"));
					return resp;
				}
				else
					return null;
			}
		}, new Object[]{code});
		
		if(ret != null)
			return ret.getResponse_msg();
		else
			return null;
	}
	
}
