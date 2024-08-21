package com.team8.teamproject.post.controller;

import com.team8.teamproject.post.domain.post;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.team8.teamproject.post.service.service;
import java.util.List;
import java.util.Optional;

@Controller
public class controller {

    private final service service;

    public controller(service service){
        this.service = service;
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