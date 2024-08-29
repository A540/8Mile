package com.team8.teamproject.oauth.dto;

import com.team8.teamproject.login.controller.dto.MemberDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
@Getter
public class PrincipalDetails implements UserDetails, OAuth2User, Serializable {

    private static final long serialVersionUID = 1L;

    // 인증된 사용자 정보
    private String name;
    private String email;
    private MemberDto member;

    // OAuth2 방식
    private Map<String, Object> attributes;

    public PrincipalDetails(MemberDto member, Map<String, Object> attributes) {
        this.member = member;
        this.name = member.getUserName();
        this.email = member.getEmail();
        this.attributes = attributes;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        // OAuth2 로그인 시 비밀번호는 필요하지 않음
        return null;
    }

    @Override
    public String getUsername() {
        return member.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // 기본값으로 설정
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // 기본값으로 설정
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // 기본값으로 설정
    }

    @Override
    public boolean isEnabled() {
        return true;  // 기본값으로 설정
    }
}
