package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ProviderWithJwtApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProviderWithJwtApplication.class, args);
	}

}
