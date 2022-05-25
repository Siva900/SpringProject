package io.sivasai.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.sivasai.dao.ClientDao;
import io.sivasai.model.ClientModel;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestClientService {

	@Autowired
	private ClientService clientService;
	
	@MockBean
	ClientDao clientDao;
	
	@Test
	public void testClientAuth() {
		
		ClientModel cltModel = new ClientModel("1","cm9vdDpyb290","Jonathan Larson");
		
		when(clientDao.getClientByToken("cm9vdDpyb290")).thenReturn(cltModel);
		assertThat(clientService.getClientByToken("cm9vdDpyb290").toString()).isEqualTo(cltModel.toString());
		
		System.out.println("client "+clientService.getClientByToken("cm9vdDpyb290").toString()+" "+cltModel.toString());
	}
	
	@Test
	public void testClientAuthInvalid() {
		
		String invalidToken = "invalidToken";
		
		when(clientDao.getClientByToken(invalidToken)).thenReturn(null);
		assertThat(clientService.getClientByToken(invalidToken)).isEqualTo(null);
		
	}
	
	@Test
	public void testClientId() {
		
		ClientModel cltModel = new ClientModel( "2","YWRtaW46dXNlck5hbW8=","Richard Williams");
		
		when(clientDao.getClientById("2")).thenReturn(cltModel);
		assertThat(clientService.getClientById("2").toString()).isEqualTo(cltModel.toString());
		
		System.out.println("client "+clientService.getClientById("2").toString()+" "+cltModel.toString());
	}
	
	@Test
	public void testClientIdInvalid() {
		
		String invalidToken = "invalidToken";
		
		when(clientDao.getClientById(invalidToken)).thenReturn(null);
		assertThat(clientService.getClientById(invalidToken)).isEqualTo(null);
		
	}
	
}
