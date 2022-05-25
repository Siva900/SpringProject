package io.sivasai.services.auth_service.Security_Interceptor;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import io.sivasai.services.AuthService;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class Security_Interceptor implements HandlerInterceptor{
	//private final Logger log = LogManager.getLogger(getClass());

	private static final String username = "username";
	private static final String header_param_authentication = "authKey";
	
	@Autowired
	AuthService authService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		 
		Map<String, String> header_map = new HashMap<String, String>();
		Boolean isValidBasicAuthRequest = false;
		
		header_map.put(username,request.getHeader(username));
		header_map.put(header_param_authentication,request.getHeader(header_param_authentication));
        
        try {

			String basicAuthKey = header_map.get(header_param_authentication);
			String basicUsername = header_map.get(username);
			
			System.out.println("values " + basicAuthKey+" "+basicUsername);
			isValidBasicAuthRequest = authService.validateBasicAuthentication(basicUsername, basicAuthKey);
			
			 System.out.println("output of authentication: "+isValidBasicAuthRequest);

			// If this is invalid request, then set the status as UNAUTHORIZED.
			if (!isValidBasicAuthRequest) {
				response.setStatus(HttpStatus.UNAUTHORIZED.value());
			}
			
			else {
				response.setStatus(HttpStatus.ACCEPTED.value());
			}
			

		} catch (Exception e) {
			System.out.println("Error occured while authenticating request : " + e.getMessage());
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
		}
        
        return isValidBasicAuthRequest;
		
	}

}
