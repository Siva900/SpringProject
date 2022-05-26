/*
 * Acknowledgements : https://www.javainuse.com/spring/bootjdbc
 * https://blog.softtek.com/en/token-based-api-authentication-with-spring-and-jwt
 * https://www.javadevjournal.com/spring/securing-a-restful-web-service-with-spring-security/
 * https://programmer.ink/think/four-ways-spring-boot-implements-timed-tasks.html
 * https://springframework.guru/java-timer/
 * https://attacomsian.com/blog/jackson-create-json-object
 * https://stackoverflow.com/questions/69934563/junit-tests-mocked-objects-are-returning-null-values
 * 
 * peerReview: Yeswanth Reddy 
 * Debugging : stackoverflow.com

	*/
package io.sivasai;

import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.sivasai.MyAppTimerClass.MyAppCustomTimer;

@SpringBootApplication
@EnableScheduling
public class MySpringProjectApplication{
	
	
	public Timer timer = new Timer(true);
	
	@Autowired
	public MyAppCustomTimer timertask;
	
	
	public static void main(String[] args) {
		SpringApplication.run(MySpringProjectApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void CallTimerTask() {
		//initial delay, timer interval
		timer.schedule(timertask,10000,10000);
	}

}
