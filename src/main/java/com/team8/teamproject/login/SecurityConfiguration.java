package com.team8.teamproject.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.RememberMeAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String MY_KEY = "1234";

    @Autowired
    private DataSource dataSource;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService users) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll() // 공개 페이지와 리소스
//                        .anyRequest().authenticated() // 나머지 페이지는 인증 필요
                )
                .formLogin(formLogin -> formLogin
                        .loginPage("/")
                        .permitAll()
                )
                .csrf(csrfConfig -> csrfConfig.disable())
                .headers(headerConfig -> headerConfig.frameOptions(frameOptionsConfig -> frameOptionsConfig.disable()))
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 로그인 페이지를 설정
                        .defaultSuccessUrl("/boards") // 로그인 성공 후 리다이렉트 URI
                        .failureUrl("/login") // 로그인 실패 시 리다이렉트 URI
                )
                .rememberMe(rememberMe -> rememberMe
                        .key("yourRememberMeKey") // Remember-Me 기능의 키
                        .tokenValiditySeconds(86400) // Remember-Me 토큰 유효 시간 (1 day)
                        .tokenRepository(persistentTokenRepository())
                        .rememberMeServices(rememberMeServices()) // Remember-Me 서비스 설정
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1) // 동시에 하나의 세션만 허용
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public RememberMeAuthenticationProvider rememberMeAuthenticationProvider() {
        return new RememberMeAuthenticationProvider(MY_KEY);
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        // 처음에는 아래 라인을 사용하여 테이블을 자동으로 생성할 수 있습니다.
        // 이후에는 주석 처리하여 테이블이 중복 생성되지 않도록 합니다.
        // tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }

    @Bean
    public RememberMeServices rememberMeServices() {
        return new TokenBasedRememberMeServices("yourRememberMeKey", userDetailsService());
    }
}
