package com.team8.teamproject.oauth;

import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import com.team8.teamproject.oauth.dto.SessionUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    private final HttpSession httpSession;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("CustomOAuth2SuccessHandler 호출됨");

        HttpSession session = request.getSession();

        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
        // 사용자 정보 가져오기
        String email = oAuth2User.getAttribute("email");
        Member user = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("No user found with email: " + email));

        // 세션에 사용자 정보 저장
        session.setAttribute("MemberDetail", new SessionUser(user));
        log.info("SESSION ID : {}", session.getId());

        // 리디렉션 URL 설정
        getRedirectStrategy().sendRedirect(request, response, "/boards");
//        super.onAuthenticationSuccess(request, response, authentication);

    }
}
