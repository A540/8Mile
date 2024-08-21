package com.team8.teamproject.post.service;

import com.team8.teamproject.post.domain.Post;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import com.team8.teamproject.post.repository.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private static final Logger log = LoggerFactory.getLogger(PostService.class);
    // 데이터베이스
    private final PostRepository postRepository;

    // Create
    public void createPost(Post post) {
        postRepository.save(post);
    }

    // Read
    public Post readPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }

    // Update
    public void editPost(Long postId, Post post) {
        Post updatePost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        updatePost.setTitle(post.getTitle());
        updatePost.setContent(post.getContent());

        postRepository.save(updatePost);
    }

    // Delete
}