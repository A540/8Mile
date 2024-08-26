package com.team8.teamproject.comments.exception;

public class PostNotFoundException extends IllegalArgumentException{

    public PostNotFoundException(){
        super("해당 게시글을 찾을 수 없습니다.");
    }
}
