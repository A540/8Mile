package com.team8.teamproject.oauth;

import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import com.team8.teamproject.oauth.dto.SessionUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Object principal = authentication.getPrincipal();
        if (principal instanceof OAuth2User) {
            OAuth2User oAuth2User = (OAuth2User) principal;
            Map<String, Object> attributes = oAuth2User.getAttributes();
            String registrationId;
            registrationId = ((OAuth2UserRequest) authentication.getDetails()).getClientRegistration().getRegistrationId();
            String userNameAttributeName = ((OAuth2UserRequest) authentication.getDetails()).getClientRegistration()
                    .getProviderDetails()
                    .getUserInfoEndpoint()
                    .getUserNameAttributeName();

            // OAuthAttributes를 생성할 때, 제공자의 속성 구조에 맞게 조정
            OAuthAttributes oauthAttributes = OAuthAttributes.of(registrationId,userNameAttributeName,attributes);
            Member user = memberRepository.findByEmail(oauthAttributes.getEmail()).orElse(null);

            if (user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", new SessionUser(user));
            }
        }

        // 리다이렉트 또는 다른 후속 작업
        response.sendRedirect("/boards");
    }
}
