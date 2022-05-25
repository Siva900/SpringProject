package io.sivasai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import io.sivasai.model.ClientModel;

@Repository
public class ClientDao extends JdbcDaoSupport{

	@Autowired
	DataSource dataSource;
	
	@PostConstruct
	private void initialize(){
		setDataSource(dataSource);
	}

	//methods with implementations
	
	
	public ClientModel getClientById(String Id) {
		String sqlQuery = "SELECT * FROM Client WHERE ID = ?";
		
		ClientModel clt = null;
				
		clt = getJdbcTemplate().query(sqlQuery, new ResultSetExtractor<ClientModel>(){

			@Override
			public ClientModel extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					ClientModel client = new ClientModel();
					client.setID(rs.getString("ID"));
					client.setName(rs.getString("name"));
					client.setAuth_token(rs.getString("Auth_token"));
					return client;
				}
				else 
					return null;
			}
		},new Object[]{Id});
		
		return clt;
	}

	
	public List<ClientModel> getAllClients() {
		String sqlQuery = "SELECT * FROM Client";
		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sqlQuery);
		List<ClientModel> return_list = new ArrayList<ClientModel>();
		
		for(Map<String, Object> row:rows){
			ClientModel client = new ClientModel();
			client.setID((String)row.get("ID"));
			client.setName((String)row.get("name"));
			client.setAuth_token((String)row.get("Auth_token"));
			return_list.add(client);
		}
		
		return return_list;
	}
	
	public ClientModel getClientByToken(String authToken) {
		
		String sqlQuery = "SELECT * FROM Client WHERE Auth_token = ?";
		ClientModel clt = null;
		
		clt = getJdbcTemplate().query(sqlQuery, new ResultSetExtractor<ClientModel>(){

			@Override
			public ClientModel extractData(ResultSet rs) throws SQLException, DataAccessException {
				if(rs.next()) {
					ClientModel client = new ClientModel();
					client.setID(rs.getString("ID"));
					client.setName(rs.getString("name"));
					client.setAuth_token(rs.getString("Auth_token"));
					return client;
				}
				else 
					return null;
			}
		},new Object[]{authToken});
		
		return clt;
	}
}
