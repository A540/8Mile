package com.team8.teamproject.board.controller.dto;

import com.team8.teamproject.post.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
