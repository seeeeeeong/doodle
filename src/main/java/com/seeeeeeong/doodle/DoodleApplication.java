package com.seeeeeeong.doodle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DoodleApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoodleApplication.class, args);
	}

}
