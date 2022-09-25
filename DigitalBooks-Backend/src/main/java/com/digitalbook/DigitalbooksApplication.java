package com.digitalbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author supriya
 * This is BookServiceApplication which will start book service
 *
 */
@EnableEurekaClient
@SpringBootApplication
public class DigitalbooksApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalbooksApplication.class, args);
	}
	
	@Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
