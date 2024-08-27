package com.team8.teamproject.login;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * Security configuration class to handle authentication and authorization.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // 공개 페이지와 리소스
//                        .anyRequest().authenticated() // 나머지 페이지는 인증 필요
                )
                // OAuth2 로그인 기능에 대한 여러 설정

                .formLogin(formLogin -> formLogin
                        .loginPage("/")
                        .permitAll()
                )
                .csrf(
                        (csrfConfig) -> csrfConfig.disable()
                )
                .headers(
                        (headerConfig) -> headerConfig.frameOptions(
                                frameOptionsConfig -> frameOptionsConfig.disable()
                        )
                )
                .oauth2Login(oauth2 -> oauth2
                .loginPage("/login") // 로그인 페이지를 설정
                .defaultSuccessUrl("/boards") // 로그인 성공 후 리다이렉트 URI
                .failureUrl("/login") // 로그인 실패 시 리다이렉트 URI
        );


        return http.build();
    }
}

