package com.team8.teamproject.board.controller.dto;

import com.team8.teamproject.post.domain.Post;

import java.time.LocalDateTime;

public class PostPageDto {

    private Long id;
    private String title;
    private LocalDateTime createdAt;


    public PostPageDto(Post post) {
        id = post.getId();
        title = post.getTitle();
        createdAt = post.getCreatedAt();
    }
}
