package com.example.tubes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class FintrackrApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintrackrApiApplication.class, args);
	}

}
