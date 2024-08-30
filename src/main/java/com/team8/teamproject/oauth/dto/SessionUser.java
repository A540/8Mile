package com.team8.teamproject.oauth.dto;

import com.team8.teamproject.login.entity.Member;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
public class SessionUser implements Serializable { // 직렬화 기능을 가진 세션 DTO

    // 인증된 사용자 정보만 필요 => name, email 필드만 선언
    private Long id;
    private String name;
    private String email;


    public
    SessionUser(Member user) {
        this.id = user.getId();
        this.name = user.getUserName();
        this.email = user.getEmail();
    }
}