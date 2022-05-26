package io.sivasai.MyAppTimerClass;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import io.sivasai.model.MessageClass;
import io.sivasai.services.MessageService;

@Component
public class MyAppCustomTimer extends TimerTask{
	
	
	@Autowired
	MessageService messageService;
	
	private HttpURLConnection httpConn;
	private Map<String, Object> queryParams;
	final private String charset = "UTF-8";
	final private String baseUrl = "https://api.gupshup.io/sm/api/v1/msg";
	final private String sender = "917834811114";
	final private String srcName = "sivasaiWhatsAppAPI";
	final private String apikey = "7lh5yhdz7krvgcjb2tj6m24vltgl2pxr";
	
	@Override
	public void run() {
		//fired when timer is reached
		//get the timer
		//run the query
		//get all messages to be executed
		//format queries into http request 
		//create headers and send them
		
		List<MessageClass> msgs = new ArrayList<MessageClass>();
		
        try {
        	msgs = messageService.PollMessages();
        }
        catch(SQLException e){
        	System.out.println(e.getMessage());
            return ;
        }
        
		
		if(msgs.isEmpty()) {
			System.out.println("this is my timer task");
			System.out.println("the return msgs is empty");
			return ;
		}
		
		System.out.println("Timer task print polled msgs: ");
		
		for (MessageClass msg : msgs) {
			System.out.println(msg.toString());
			try {
				HttpPostForm(msg);
				
			} catch (JsonParseException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("this is my timer task");
		
	}

	public int HttpPostForm(MessageClass msg) throws JsonParseException {
		
		String response = "";
		int status = 0;
		
		//construct query parameters from msg
		this.queryParams = new HashMap<>();
		queryParams.put("channel", "whatsapp");
		queryParams.put("source", sender);
		queryParams.put("src.name", srcName);
		queryParams.put("destination", msg.getRecipient_number());
		queryParams.put("disablePreview", true);
		
		
	    ObjectMapper mapper = new ObjectMapper();
	    ObjectNode user = mapper.createObjectNode();
	    user.put("type", "text");
	    user.put("text", msg.getMessage_body());
	    
	    String jsonParam = null;
		try {
			jsonParam = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	    
	    queryParams.put("message", jsonParam);
        
	    try {
	        URL url = new URL(baseUrl);
	        httpConn = (HttpURLConnection) url.openConnection();
	        httpConn.setRequestMethod("POST");
	        
	        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        httpConn.setRequestProperty("apikey", apikey);
	        httpConn.setRequestProperty("Accept", "application/json");
	        
	        httpConn.setUseCaches(false);
	        httpConn.setDoOutput(true);
	        httpConn.setDoInput(true);
	        
	        //putting together and finishing the request
	        byte[] postDataBytes = this.getParamsByte(queryParams);
	        httpConn.getOutputStream().write(postDataBytes);
	        
	        status = httpConn.getResponseCode();
	        
	        if (status == HttpURLConnection.HTTP_ACCEPTED) {
	            ByteArrayOutputStream result = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = httpConn.getInputStream().read(buffer)) != -1) {
	                result.write(buffer, 0, length);
	            }
	            
	            response = result.toString(this.charset);
	            messageService.UpdateDatabase(status,1,msg.getID());
	            messageService.UpdateTriesCount(msg.getID());
	            System.out.println("response: "+response);
	            
	        }
	        
	        else {
	        	
	        	ByteArrayOutputStream result = new ByteArrayOutputStream();
	            byte[] buffer = new byte[1024];
	            int length;
	            while ((length = httpConn.getInputStream().read(buffer)) != -1) {
	                result.write(buffer, 0, length);
	            }
	            
	            response = result.toString(this.charset);
	            
	            if(status==400 || status==500) {
	            	messageService.UpdateDatabase(status,-1,msg.getID());
	            }
	            else if(messageService.getMessageById(msg.getID()).getTries_count() == 5) {
	            	//max limit of retries reached
	            	messageService.UpdateDatabase(status,-1,msg.getID());
	            }
	            else {
	            	messageService.UpdateTriesCount(msg.getID());
	            }
	            
	        	System.out.println("Print response error status code "+status);
	        	
	        }
	        
        }
        catch (MalformedURLException e) {  
        	e.printStackTrace();  
        }  
        catch (IOException e) {  
        	e.printStackTrace();  
        }
	    
	    return status;
	}
	
	private byte[] getParamsByte(Map<String, Object> params) {
		
        byte[] result = null;
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String, Object> param : params.entrySet()) {
            if (postData.length() != 0) {
                postData.append('&');
            }
            postData.append(this.encodeParam(param.getKey()));
            postData.append('=');
            postData.append(this.encodeParam(String.valueOf(param.getValue())));
        }
        try {
            result = postData.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	private String encodeParam(String data) {
        String result = "";
        try {
            result = URLEncoder.encode(data, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
	
	

}
