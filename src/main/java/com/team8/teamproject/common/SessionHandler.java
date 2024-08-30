package com.team8.teamproject.common;

import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.oauth.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionHandler {

    //== 세션 로그인 멤버 정보 ==//
    public MemberDto getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (MemberDto) session.getAttribute("userDetails") : null;
    }


//    //== 구글 로그인 세션 정보 ==//
//    public SessionUser getGoogleLoggedInUser(HttpServletRequest request) {
//
//        HttpSession session1 = request.getSession(false);
//        log.info("SESSION ID : {}", session1.getId());
//        return (SessionUser) session1.getAttribute("MemberDetail");
//    }

    //== 구글 로그인 세션 정보 ==//
    public SessionUser getGoogleLoggedInUser(HttpServletRequest request) {
        HttpSession session1 = request.getSession(false);

        if (session1 == null) {
            log.warn("구글 로그인 세션이 존재하지 않습니다.");
            return null;
        }

        log.info("SESSION ID : {}", session1.getId());
        return (SessionUser) session1.getAttribute("MemberDetail");
    }

}
