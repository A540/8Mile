package com.team8.teamproject.post.service;

import com.team8.teamproject.post.domain.Post;
import lombok.RequiredArgsConstructor;
import com.team8.teamproject.post.repository.PostRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {
    // 데이터베이스
    private final PostRepository postRepository;

    // Create
    public void createPost(Post post) {
        postRepository.save(post);
    }

}