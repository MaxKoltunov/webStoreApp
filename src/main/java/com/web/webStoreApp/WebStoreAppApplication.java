package com.web.webStoreApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebStoreAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebStoreAppApplication.class, args);
	}

}
