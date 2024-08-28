package com.team8.teamproject.login.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team8.teamproject.login.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto implements Serializable {

    private String userName;
    private String email;

    @JsonCreator
    public MemberDto(@JsonProperty("userName") String userName,
                     @JsonProperty("email") String email) {
        this.userName = userName;
        this.email = email;
    }

    // Member 객체를 MemberDto로 변환하는 생성자
    public MemberDto(Member member) {
        this.userName = member.getUserName();
        this.email = member.getEmail();
    }
}
