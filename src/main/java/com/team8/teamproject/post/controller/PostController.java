package com.team8.teamproject.post.controller;

import com.team8.teamproject.post.domain.Post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.team8.teamproject.post.service.PostService;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PostController {

    private final PostService service;

    public PostController(PostService service){
        this.service = service;
    }

    @GetMapping("/posts")
    public String getPost() {
        return "post/post";
    }

    @GetMapping("/posts/create")
    public String getCreatePost() {
        return "post/createPost";
    }

    @GetMapping("/posts/edit")
    public String getEditPost() {
        return "post/editPost";
    }

    //Create
    @PostMapping("/posts/create")
    public void createPost(Post post){
        service.createPost(post);
    }
}