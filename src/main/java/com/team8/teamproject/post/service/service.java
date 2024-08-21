package com.team8.teamproject.post.service;

import com.team8.teamproject.post.domain.post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.team8.teamproject.post.repository.postRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class service {
    // 데이터베이스
    private final postRepository postRepository;


    //Read
    public Optional<post> findById(Long postId) {
        Optional<post> post = postRepository.findById(postId);
        return post;
    }
}