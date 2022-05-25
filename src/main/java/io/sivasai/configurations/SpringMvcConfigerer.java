package io.sivasai.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.sivasai.services.auth_service.Security_Interceptor.Security_Interceptor;

@Configuration
public class SpringMvcConfigerer implements WebMvcConfigurer{
	
	@Autowired
	private Security_Interceptor security_Interceptor;
	
	@Override
	public void addInterceptors(final InterceptorRegistry registry) {
		registry.addInterceptor(security_Interceptor);
	}

}
