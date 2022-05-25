package io.sivasai.dao;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import io.sivasai.model.ClientModel;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientDaoTest {

	@Autowired
	private ClientDao clientDao;
	
	
	@Test
	public void testClientAuth() {
		
		String NewToken = "TXluYW1laXNXaWxsaWFtOlBhc3N3b3Jk";
		
		ClientModel cltModel = new ClientModel("3","TXluYW1laXNXaWxsaWFtOlBhc3N3b3Jk","steven spielberg");
		
		ClientModel actualResult = clientDao.getClientByToken(NewToken);
		
		assertThat(actualResult.toString()).isEqualTo(cltModel.toString());
		
		System.out.println("actual client "+actualResult.toString()+" model client "+cltModel.toString());
	}
	
	@Test
	public void testClientAuthInvalid() {
		
		String invalidToken = "invalidToken";
		
		ClientModel actualResult = null;
		
		actualResult = clientDao.getClientByToken(invalidToken);
        
		assertThat(actualResult).isEqualTo(null);
		
	}
	
	@Test
	public void testClientId() {
		
		ClientModel cltModel = new ClientModel( "2","YWRtaW46dXNlck5hbW8=","Richard Williams");
		
		assertThat(clientDao.getClientById("2").toString()).isEqualTo(cltModel.toString());
		
		System.out.println("client "+clientDao.getClientById("2").toString()+" "+cltModel.toString());
		
	}
	
	@Test
	public void testClientIdInvalid() {
		
		String invalidToken = "invalidToken";
		ClientModel actualResult = null;
		
		actualResult = clientDao.getClientById(invalidToken);
        
		assertThat(actualResult).isEqualTo(null);
		
	}
	
}
