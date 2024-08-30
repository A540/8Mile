package com.team8.teamproject.board.controller.dto;

import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import lombok.Data;

import java.io.Serializable;

@Data
public class MemberViewDto implements Serializable {

    private Long id;
    private String userName;
    private String email;

    public MemberViewDto(Member member) {
        this.id = member.getId();
        this.userName = member.getUserName();
        this.email = member.getEmail();
    }

    public MemberViewDto(MemberDto memberDto) {
        this.id = memberDto.getId();
        this.userName = memberDto.getUserName();
        this.email = memberDto.getEmail();
    }
}
