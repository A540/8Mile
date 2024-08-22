package com.team8.teamproject.comments.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor    // DI
@RestController
public class CommentController {

    private final CommentService commentService;

    // 게시글의 id를 받아 해당 댓글만 모두 출력
    @GetMapping("/comment/{postId}")
    public ResponseEntity<List<Comments>> findComments(@PathVariable("postId") Long postId){
        List<Comments> Comments = commentService.findAll(postId);
        return new ResponseEntity<>(Comments, HttpStatus.OK);
    }

//    // 댓글 생성 API
//    @PostMapping("/comment/{postId}")
//    public ResponseEntity<Comments> saveComments(@PathVariable("postId") Long id, @RequestBody AddCommentRequest addDTO){
//        Comments savedComment = commentService.save(id, addDTO);
//        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
//    }

    // 댓글 생성
    @PostMapping("/comment/{postId}")
    public ResponseEntity<Comments> saveComments(@PathVariable("postId") Long id, @RequestParam AddCommentRequest addDTO){
        Comments savedComment = commentService.save(id, addDTO);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

//    // 댓글 수정 API
//    @PostMapping("/comment/{commentId}/edit")
//    public ResponseEntity<Comments> updateComments(@PathVariable("commentId") Long id, @RequestBody AddCommentRequest updateDTO){
//        Comments updatedComment = commentService.update(id, updateDTO);
//        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
//    }


    // 댓글 수정
    @PostMapping("/comment/{commentId}/edit")
    public ResponseEntity<Comments> updateComments(@PathVariable("commentId") Long id, @RequestParam AddCommentRequest updateDTO){
        Comments updatedComment = commentService.update(id, updateDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable("commentId") Long id){
        commentService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
