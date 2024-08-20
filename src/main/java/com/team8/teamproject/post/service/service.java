package com.team8.teamproject.post.service;

import com.team8.teamproject.post.domain.post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.team8.teamproject.post.repository.postRepository;

@Service
@RequiredArgsConstructor
public class service {
    // 데이터베이스
    private final postRepository postRepository;

    // Create
    public void createPost(post post) {
        postRepository.save(post);
    }

}