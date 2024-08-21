package com.team8.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableWebMvc
@SpringBootApplication
@EnableJpaAuditing
public class TeamprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamprojectApplication.class, args);
	}

}
