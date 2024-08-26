package com.team8.teamproject.comments.exception;

public class CommentNotFoundException extends IllegalArgumentException{

    public CommentNotFoundException(){
        super("해당 댓글을 찾을 수 없습니다.");
    }
}
