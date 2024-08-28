package com.team8.teamproject.post.service;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.repository.CommentRepository;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.domain.Rating;
import com.team8.teamproject.post.domain.RatingId;
import com.team8.teamproject.post.repository.RatingRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import com.team8.teamproject.post.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    // 데이터베이스
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    // 별점
    private final RatingRepository ratingRepository;

    // 게시글 상세 조회
    @Transactional(readOnly = true)
    public List<Comments> readComment(Post post) {
        List<Comments> readPost = commentRepository.findByPost(post);
        return readPost;
    }

    // Create, 파일 업로드 추상화로 StorageLocalImpl에 구현

    // Read
    @Transactional(readOnly = true)
    public Post readPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }

    // Update
    @Transactional
    public void editPost(Long postId, Post post) {
        Post updatePost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        updatePost.setTitle(post.getTitle());
        updatePost.setContent(post.getContent());

        postRepository.save(updatePost);
    }

    // Delete
    @Transactional
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }
    
    // 별점 기능 관련
    public void createRating(Long postId, long memberId, double rating) {
        RatingId saveRatingId = new RatingId(postId, memberId);

        // 기본키가 복합키 이므로 유저 당 게시글 1개의 별점만 Create, Update
        Rating saveRating = new Rating(saveRatingId, rating);
        ratingRepository.save(saveRating);
    }

    public Double getRatingAVG(Long postId) {
        Double ratingAVG = ratingRepository.findAvgRating(postId);
        if(ratingAVG == null){
            ratingAVG = 0.0;
        }
        return ratingAVG;
    }

    public Integer getCountRating(Long postId) {
        return ratingRepository.findCountRating(postId);
    }
}