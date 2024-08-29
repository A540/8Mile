package com.team8.teamproject;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRegistration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.rowset.serial.SerialException;

@EnableWebMvc	//빈설정초기화 ->WebMvcConfig로 수동설정
@SpringBootApplication
@EnableJpaAuditing
@EnableRedisHttpSession
public class TeamprojectApplication implements WebApplicationInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TeamprojectApplication.class, args);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
		DispatcherServlet dispatcherServlet = new DispatcherServlet(applicationContext);
		dispatcherServlet.setThreadContextInheritable(true);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcherServlet", dispatcherServlet);
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
	}
}

