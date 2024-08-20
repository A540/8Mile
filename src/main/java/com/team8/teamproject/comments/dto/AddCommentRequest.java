package com.team8.teamproject.comments.dto;

import com.team8.teamproject.comments.domain.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
    private String content;
}