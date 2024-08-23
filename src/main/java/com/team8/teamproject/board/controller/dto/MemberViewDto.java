package com.team8.teamproject.board.controller.dto;

import com.team8.teamproject.login.entity.Member;
import lombok.Data;

@Data
public class MemberViewDto {

    private Long id;
    private String userName;
    private String email;

    public MemberViewDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUserName();
        this.email = member.getEmail();
    }
}
