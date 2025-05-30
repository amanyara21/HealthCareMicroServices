package com.bodypart_service.BodypartService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BodypartServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BodypartServiceApplication.class, args);
	}

}
