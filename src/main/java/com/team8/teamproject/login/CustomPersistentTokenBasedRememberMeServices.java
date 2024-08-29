package com.team8.teamproject.login;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


public class CustomPersistentTokenBasedRememberMeServices extends PersistentTokenBasedRememberMeServices {


    public CustomPersistentTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService, PersistentTokenRepository tokenRepository) {
        super(key, userDetailsService, tokenRepository);
    }

    public void customLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication successfulAuthentication) {
        if (successfulAuthentication.isAuthenticated() && (Boolean) successfulAuthentication.getDetails()) {
            super.onLoginSuccess(request, response, successfulAuthentication);
        }
    }
}