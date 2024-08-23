package com.team8.teamproject.post.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.post.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.team8.teamproject.post.service.PostService;

import java.util.List;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    // 전체 게시글 조회
    /*
    @GetMapping("/board/{boardId}")
    public String getBoardPost(@PathVariable Long boardId, Model model){
        List<Post> readPost = postService.readByBoardId(boardId);
        Board readBoard = postService.readBoard(boardId);
        model.addAttribute("post", readPost);
        model.addAttribute("board", readBoard);
        return "board/board";
    }
    */
    // Create
    @GetMapping("/posts/create")
    public String getCreatePost(@RequestParam(value = "boardId") Long boardId, Model model){
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }
    @PostMapping("/posts/create")
    public String createPost(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "title") String title, @RequestParam(value = "content") String content) {
        postService.createPost(boardId, title, content);
        return "redirect:/boards/" + boardId;
    }

    // Read, 게시글 상세 조회
    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable Long postId, Model model) {
        Post readPost = postService.readPost(postId);
        List<Comments> readComment = postService.readComment(readPost);
        model.addAttribute("post", readPost);
        model.addAttribute("comments", readComment);
        return "post/post";
    }

    // Update
    @GetMapping("/posts/{postId}/edit")
    public String getEditPost(@PathVariable Long postId, Model model){
        Post readPost = postService.readPost(postId);
        model.addAttribute("post", readPost);
        return "post/editPost";
    }
    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable Long postId, Post post) {
        postService.editPost(postId, post);
        return "redirect:/posts/" + postId;
    }

    // Delete
    @DeleteMapping("/posts/{postId}")
    public void getDeletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }
}