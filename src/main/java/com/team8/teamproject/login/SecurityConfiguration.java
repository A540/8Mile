package com.team8.teamproject.login;

import com.team8.teamproject.oauth.CustomAuthenticationSuccessHandler;
import com.team8.teamproject.oauth.CustomOAuth2SuccessHandler;
import com.team8.teamproject.oauth.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String MY_KEY = "1234";
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    public SecurityConfiguration(CustomOAuth2SuccessHandler customOAuth2SuccessHandler) {
        this.customOAuth2SuccessHandler = customOAuth2SuccessHandler;
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // 세션 생성 정책 설정
                        .sessionFixation().migrateSession() // 세션 고정 보호를 유지하면서 세션 ID를 변경합니다.
                )
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll() // 공개 페이지와 리소스
//                        .anyRequest().authenticated() // 나머지 페이지는 인증 필요
                )

                .oauth2Login(oauth2 -> oauth2

                        .loginPage("/login") // 로그인 페이지를 설정
                        .successHandler(customOAuth2SuccessHandler) // OAuth2 로그인 성공 핸들러 설정
//                        .defaultSuccessUrl("/post") // 로그인 성공 후 리다이렉트 URI
                        .failureUrl("/login") // 로그인 실패 시 리다이렉트 URI

                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/login.html")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login")  // 로그아웃 성공 후 리디렉션 경로
                        .invalidateHttpSession(true)  // 세션 무효화
                        .deleteCookies("JSESSIONID", "remember-me")
                )
                .csrf(csrfConfig -> csrfConfig.disable())
                .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))

                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // 동시에 하나의 세션만 허용
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withUsername("user1")
                .password("{noop}user1Pass")
                .authorities("ROLE_USER")
                .build();
        UserDetails admin = User.withUsername("admin1")
                .password("{noop}admin1Pass")
                .authorities("ROLE_ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    @Bean
    public PersistentTokenBasedRememberMeServices rememberMeServices() {
        return new PersistentTokenBasedRememberMeServices(MY_KEY, userDetailsService(), persistentTokenRepository());
    }

}
