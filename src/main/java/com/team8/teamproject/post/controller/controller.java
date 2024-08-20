package com.team8.teamproject.post.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.team8.teamproject.post.service.service;

@RestController
public class controller {

    private final service service;

    public controller(service service){
        this.service = service;
    }

    @GetMapping("/post")
    public String hello() {
        return "Hello";
    }
}