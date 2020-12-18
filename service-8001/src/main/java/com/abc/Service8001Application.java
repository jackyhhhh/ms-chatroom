package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Service8001Application {

	public static void main(String[] args) {
		SpringApplication.run(Service8001Application.class, args);
	}

}
