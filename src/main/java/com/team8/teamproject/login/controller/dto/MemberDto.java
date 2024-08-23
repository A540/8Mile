package com.team8.teamproject.login.controller.dto;


import com.team8.teamproject.login.entity.Member;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

    @NonNull
    private String userName;


    public MemberDto(Member member)  {
        this.userName = member.getUserName();
    }
}
