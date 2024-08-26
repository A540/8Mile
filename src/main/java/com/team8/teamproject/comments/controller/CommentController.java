package com.team8.teamproject.comments.controller;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.service.CommentService;
import com.team8.teamproject.login.entity.Member;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class CommentController {


    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public String saveComments(@RequestParam("postId") Long id, @RequestParam("content") String content, HttpSession session){
        //세션에 저장한 loggedInUser 값을 사용해 Member 객체 가져오기
        Member member = (Member) session.getAttribute("loggedInUser");
        // 로그인을 하지 않았다면 로그인 페이지로 전환
        if(member == null){
            return "redirect:/";
        }

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

    @GetMapping("/comments/{commentId}/like")
    public String likeThisComment(@RequestHeader(value = HttpHeaders.REFERER, required = false) final String referrer, @PathVariable("commentId") Long id, HttpSession session){
        // 로그인을 하지 않았다면 로그인 페이지로 전환
        if(session.getAttribute("loggedInUser") == null){
            return "redirect:/";
        }

        commentService.addLike(id);
        return "redirect:" + referrer;
    }
}
