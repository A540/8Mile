package com.team8.teamproject.comments.dto;

import com.team8.teamproject.login.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class ReadCommentResponse {

    private Long id;

    private String content;

    private int likeCount;

    private LocalDateTime createdAt;

    private Member member;
}
