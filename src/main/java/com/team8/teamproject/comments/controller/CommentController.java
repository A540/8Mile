package com.team8.teamproject.comments.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<Comments> saveComments(@PathVariable("postId") Long id, AddCommentRequest addDTO){
        Comments savedComment = commentService.save(id, addDTO);
        return new ResponseEntity<Comments>(savedComment, HttpStatus.CREATED);
    }

}
