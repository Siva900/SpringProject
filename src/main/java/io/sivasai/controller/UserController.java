package io.sivasai.controller;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.sivasai.services.AuthService;
import io.sivasai.services.ClientService;
import io.sivasai.services.MessageService;

@RestController
@RequestMapping(path="/message_scheduler")
public class UserController {
	
	@Autowired
	ClientService clientService;
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	AuthService authService;
	
	@GetMapping("/helloMessage")
	public String test_working() {
		return "Welcome to my whatsapp api!!";
	}
	
	@RequestMapping(method=RequestMethod.POST,value="/messages")
	public String getMessage(HttpServletResponse response,HttpServletRequest request,@RequestHeader(value="authKey") String auth_token, @RequestBody Map<String, String> requestBody) throws ParseException, SQLException{
		
				//get details from header : content type and apikey
				//perform authorization : security interceptor
		
		String recipient_number = requestBody.get("recipient_number");
		String scheduled_time = requestBody.get("scheduled_time");
		String message_body = requestBody.get("message");
		
		String client_id = clientService.getClientByToken(auth_token).getID();
		
		System.out.println("client id "+client_id);
		
		//performing validation
		Boolean flag = authService.ValidateParameters(recipient_number, scheduled_time, message_body);
		
		//If validation successful then insert message into the database
		//if fails then sent error response to the localhost
		if(flag == true) {
			messageService.InsertMessage(client_id, message_body, recipient_number, scheduled_time);
			response.setStatus(HttpServletResponse.SC_ACCEPTED);
			return "Welcome to this app user your message is successfully received!!";
		}
		else {
			System.out.println("Error in parameters validation");
			response.setStatus(HttpServletResponse.SC_NOT_ACCEPTABLE);
			
			return "Validation Error in request body parameters";
		}
		
		
	}
}
