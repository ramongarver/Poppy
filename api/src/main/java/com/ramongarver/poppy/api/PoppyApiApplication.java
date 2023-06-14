package com.ramongarver.poppy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class PoppyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoppyApiApplication.class, args);
	}

}
