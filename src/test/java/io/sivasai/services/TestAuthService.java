package io.sivasai.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.text.ParseException;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import io.sivasai.model.ClientModel;


@SpringBootTest
@RunWith(SpringRunner.class)
public class TestAuthService {

	@Autowired
	private AuthService authService;
	
	@MockBean
	ClientService clientService;
	
	//test Authkey
	
	@Test
	public void testAuthKey() {
		
		ClientModel cltModel = new ClientModel("1","cm9vdDpyb290","Jonathan Larson");
		
		when(clientService.getClientByToken("cm9vdDpyb290")).thenReturn(cltModel);
		assertThat(authService.validateBasicAuthentication("Jonathan Larson","cm9vdDpyb290")).isEqualTo(true);
		
	}
	
	@Test
	public void testAuthKeyInvalid() {
		
		String invalid = "invalid token";
		
		
		when(clientService.getClientByToken(invalid)).thenReturn(null);
		assertThat(authService.validateBasicAuthentication(invalid,"Jonathan Larson")).isEqualTo(false);
		
	}
	
	//for validation
	
	@Test
	public void testValidation() throws ParseException {
		
		assertEquals(authService.ValidateParameters("919542684424", "2022-05-26 22:13:00"),true);
		
	}
	
	@Test
	public void testValidationInvalid() throws ParseException {
		
		assertEquals(authService.ValidateParameters("91954264424", "2022-05-22 22:13:00"),false);
		
	}
	
}
