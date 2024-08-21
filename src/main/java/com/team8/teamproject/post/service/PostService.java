package com.team8.teamproject.post.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.repository.CommentRepository;
import com.team8.teamproject.post.domain.Post;
import lombok.RequiredArgsConstructor;
import com.team8.teamproject.post.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    // 데이터베이스
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 전체 게시글 조회
    public List<Post> readByBoardId(Long boardId) {
        return postRepository.findAll()
                .stream()
                .map(Post::new)
                .toList();
    }

    public Board readBoard(Long boardId) {
        Board board = boardRepository.findOne(boardId).orElseThrow(IllegalArgumentException::new);
        return board;
    }

    // 게시글 상세 조회
    /*
    public List<Comments> readComment(Post post) {
        List<Comments> readPost = commentRepository.findByPost(post);
        readPost.stream()
                .map(Comments::new)
                .toList();
        return readPost;
    }
    */
    
    // Create
    public void createPost(Long boardId, String title, String content) {
        Board board = boardRepository.findOne(boardId).orElseThrow(IllegalArgumentException::new);
        Post savePost = new Post(board, title, content);
        postRepository.save(savePost);
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
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }
}