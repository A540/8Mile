package com.team8.teamproject.login.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team8.teamproject.login.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // 직렬화 호환성 유지

    private Long id;
    private String userName;
    private String email;

    // JSON 직렬화 및 역직렬화 시 사용될 기본 생성자 및 getter/setter 메소드가 있어야 함
    public MemberDto(Long id,
             String userName,
             String email) {
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

    // Member 객체를 MemberDto로 변환하는 생성자
    public MemberDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUserName();
        this.email = member.getEmail();
    }
}
