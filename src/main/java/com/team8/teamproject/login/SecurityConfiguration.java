//package com.team8.teamproject.login;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
///**
// * Security configuration class to handle authentication and authorization.
// */
//@Configuration
//@EnableWebSecurity
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .anyRequest().permitAll() // 공개 페이지와 리소스
////                        .anyRequest().authenticated() // 나머지 페이지는 인증 필요
//                )
//                // OAuth2 로그인 기능에 대한 여러 설정
//
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/boards")
//                        .permitAll()
//                )
//                .csrf().and();
////                .oauth2Login(Customizer.withDefaults()); // 아래 코드와 동일한 결과
//
//
//        return http.build();
//    }
//}
//
