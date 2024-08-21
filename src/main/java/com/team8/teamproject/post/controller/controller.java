package com.team8.teamproject.post.controller;

import com.team8.teamproject.post.domain.post;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.team8.teamproject.post.service.service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class controller {

    private final service service;

    public controller(service service){
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
    public void createPost(post post){
        service.createPost(post);
    }
}