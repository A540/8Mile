//package com.team8.teamproject.login;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
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
//@EnableWebSecurity(debug = true)
//public class SecurityConfiguration {
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        // @formatter:off
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(formLogin -> formLogin
//                        .loginPage("/login")
//                        .defaultSuccessUrl("/login", true) // 로그인 성공 후 리디렉션할 URL 설정
//                        .permitAll()
//                )
//                .httpBasic(withDefaults()); // 기본 인증 비활성화
//        // @formatter:on
//        return http.build();
//    }
//
//    @Bean
//    public InMemoryUserDetailsManager userDetailsService() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//}
