package com.team8.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc	//빈설정초기화 ->WebMvcConfig로 수동설정
@SpringBootApplication
@EnableJpaAuditing
@EnableRedisHttpSession
public class TeamprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(TeamprojectApplication.class, args);
	}

}

