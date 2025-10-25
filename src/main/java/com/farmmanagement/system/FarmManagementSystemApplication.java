package com.farmmanagement.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FarmManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(FarmManagementSystemApplication.class, args);
	}

}
