package com.team8.teamproject.comments.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.service.CommentService;
import com.team8.teamproject.login.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CommentController {


    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public String saveComments(@RequestParam("postId") Long id, @RequestParam("content") String content, HttpSession session){

        Member member = (Member) session.getAttribute("loggedInUser");

        Comments comment = commentService.save(id, content, member);

        return "redirect:/posts/" + id;
    }

    // 댓글 수정
    @PostMapping("/comments/{commentId}/edit")
    public String updateComments(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, @PathVariable("commentId") Long id, @RequestParam String content){
        // -> HttpHeaders.REFERER = 요청을 보낸 시점에 url
        Comments updatedComment = commentService.update(id, content);
        return "redirect:" + referrer;
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public String deleteComment(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, @PathVariable("commentId") Long id){
        commentService.delete(id);
        return "redirect:" + referrer;
    }
}
