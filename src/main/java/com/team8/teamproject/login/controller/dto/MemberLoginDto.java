package com.team8.teamproject.login.controller.dto;


import com.team8.teamproject.login.entity.Member;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MemberLoginDto {
    @NonNull
    private String email;
    private String password;

    public MemberLoginDto(Member member)  {
        this.email = member.getEmail();
        this.password = member.getPassword();
    }
}
