package com.team8.teamproject.post.controller;

import com.team8.teamproject.post.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.team8.teamproject.post.service.PostService;

@Controller
public class PostController {

    private final PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    // Create
    @GetMapping("/posts/create")
    public String getCreatePost(){
        return "post/createPost";
    }
    @PostMapping("/posts/create")
    public void createPost(Post post) {
        postService.createPost(post);
    }

    // Read boardId, CommentId 연관 관계 설정 후 진행
    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable Long postId, Model model) {
        Post readPost = postService.readPost(postId);
        model.addAttribute("post", readPost);
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
    public void editPost(@PathVariable Long postId, Post post) {
        postService.editPost(postId, post);
    }

    // Delete Read 완성 후 진행
    /*
    @GetMapping("/posts/{postId}/delete")
    public String getDeletePost(@PathVariable Long postId) {
    }
    */
}