package com.team8.teamproject.comments.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// 저장과 수정시에 요청으로 받을 DTO
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private String content;
}