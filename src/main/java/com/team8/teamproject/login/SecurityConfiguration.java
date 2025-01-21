package com.team8.teamproject.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team8.teamproject.login.service.CustomPrincipalDetailService;
//import com.team8.teamproject.oauth.CustomAuthenticationSuccessHandler;
import com.team8.teamproject.oauth.CustomOAuth2UserService;
import com.team8.teamproject.oauth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
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

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfiguration {

    private static final String MY_KEY = "1234";

     private final CustomPrincipalDetailService customPrincipalDetailService;

     private final ObjectMapper objectMapper;

//    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    private final  CustomOAuth2UserService customOAuth2UserService;

    private final PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                                .anyRequest().permitAll() // 공개 페이지와 리소스
//                        .anyRequest().authenticated() // 나머지 페이지는 인증 필요
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
                .oauth2Login(oauth2 -> oauth2
                        .loginPage("/login") // 로그인 페이지를 설정
                        .defaultSuccessUrl("/boards") // 로그인 성공 후 리다이렉트 URI
                        .failureUrl("/login") // 로그인 실패 시 리다이렉트 URI
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService))

                )
                .rememberMe(rememberMe -> rememberMe
                        .userDetailsService(customPrincipalDetailService) // Remember-Me 설정
                        .rememberMeParameter("remember-me")
                        .key(MY_KEY)
                        .tokenValiditySeconds(86400) // 24시간
                        .alwaysRemember(true)
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .maximumSessions(1)
                );

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager() {//- AuthenticationManager 등록 ->인증을 만들고 처리하는 인터페이스.
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();//DaoAuthenticationProvider 사용
        provider.setPasswordEncoder(passwordEncoder);//PasswordEncoder로는 bCryPasswordEncoder를 사용 암호화는 이친구로 설정.
        provider.setUserDetailsService(customPrincipalDetailService); //유저 인증절차는 이친구에게 넘김.
        return new ProviderManager(provider); //인증은 provider에게 넘김.
    }


}
