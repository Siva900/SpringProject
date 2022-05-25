package io.sivasai.configurations;

import java.util.Timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimerConfigurer {
	
	@Bean
    public Timer getTimer(){
        return new Timer();
    }
	
}
