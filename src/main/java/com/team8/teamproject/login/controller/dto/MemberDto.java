package com.team8.teamproject.login.controller.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.oauth.dto.SessionUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto implements Serializable {

    private Long id;
    private String userName;
    private String email;

    @JsonCreator
    public MemberDto(
            @JsonProperty("userName") Long id,
                @JsonProperty("userName") String userName,
                     @JsonProperty("email") String email) {
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

    public MemberDto (SessionUser sessionUser) {
               this.id= sessionUser.getId();
                this.userName= sessionUser.getName();
                this.email = sessionUser.getEmail();
    }

}
