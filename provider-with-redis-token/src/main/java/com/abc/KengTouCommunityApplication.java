package com.abc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class KengTouCommunityApplication {

	public static void main(String[] args) {
		SpringApplication.run(KengTouCommunityApplication.class, args);
	}

}
