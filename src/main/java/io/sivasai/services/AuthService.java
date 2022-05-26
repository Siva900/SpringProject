package io.sivasai.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import io.sivasai.model.ClientModel;

@Service
public class AuthService{
	
	@Autowired
	ClientService clientService;
	
	public Boolean validateBasicAuthentication(String userName, String basicAuthValue) {
		
		Boolean flag = false;
		ClientModel client = clientService.getClientByToken(basicAuthValue);
		
		//System.out.println("client service output: "+client.getAuth_token()+" "+client.getName()+" "+client.getID());
		//System.out.println("param values: "+basicAuthValue+" "+userName+" "+userID);
		if(client != null)
			flag = Objects.equals(client.getAuth_token(),basicAuthValue) && Objects.equals(userName,client.getName());
		
		System.out.println("authService output "+flag);
		return flag;
	}
	
	public Boolean ValidateParameters(String phone_number, String scheduled_time, String message) throws ParseException{
		Boolean flag = false;
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		
		System.out.println("Current time in auth service "+dtf.format(now));
		   
		String arrival_time = dtf.format(now);
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
		
		System.out.println("Current time in auth service "+phone_number);
		Date dateTime1 = formatter.parse(arrival_time);    
		System.out.println("Current time in auth service 1 "+scheduled_time);
	    Date dateTime2 = formatter.parse(scheduled_time);
	    System.out.println("Current time in auth service 2");
	    
		Boolean bool1 = dateTime1.before(dateTime2);
		int length = message.length();
		if(phone_number.length() == 12 && phone_number.substring(0, 2).equals("91") == true && bool1 && length!=0)
			flag = true;
		
		System.out.println("auth service final flag: "+flag);
		
		return flag;
	}

}
