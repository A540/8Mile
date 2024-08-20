package com.team8.teamproject.comments.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<Comments>> findComments(@PathVariable("postId") Long postId){
        List<Comments> Comments = commentService.findAll(postId);
        return new ResponseEntity<>(Comments, HttpStatus.OK);
    }

    @PostMapping("/comment/{postId}")
    public ResponseEntity<Comments> saveComments(@PathVariable("postId") Long id, @RequestBody AddCommentRequest addDTO){
        Comments savedComment = commentService.save(id, addDTO);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

    @PostMapping("/comment/{commentId}/edit")
    public ResponseEntity<Comments> updateComments(@PathVariable("commentId") Long id, @RequestBody AddCommentRequest updateDTO){
        Comments updatedComment = commentService.update(id, updateDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

}
