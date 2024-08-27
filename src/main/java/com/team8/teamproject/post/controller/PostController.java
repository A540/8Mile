package com.team8.teamproject.post.controller;

import com.team8.teamproject.comments.service.CommentService;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.storage.StorageService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.team8.teamproject.post.service.PostService;
import org.springframework.web.multipart.MultipartFile;
import com.team8.teamproject.comments.dto.ReadCommentResponse;

import java.io.IOException;
import java.util.List;

@Controller
public class PostController {

    private final PostService postService;
    private final StorageService storageService;
    private final CommentService commentService;

    public PostController(PostService postService, StorageService storageService, CommentService commentService){
        this.postService = postService;
        this.storageService = storageService;
        this.commentService = commentService;
    }

    // Create, 파일 업로드
    @GetMapping("/posts/create")
    public String getCreatePost(@RequestParam(value = "boardId") Long boardId, Model model, HttpSession session){
        model.addAttribute("boardId", boardId);
        return "post/createPost";
    }

    @PostMapping("/posts/create")
    public String createPost(@RequestParam(value = "boardId") Long boardId, @RequestParam(value = "title") String title,
                             @RequestParam(value = "content") String content, @RequestParam(value = "file") MultipartFile files,
                             HttpSession session) throws IOException {
        Member loginMember = (Member) session.getAttribute("loggedInUser");
        storageService.createLocalPost(boardId, title, content, files, loginMember);
        return "redirect:/boards/" + boardId;
    }

    // Read, 게시글 상세 조회
    @GetMapping("/posts/{postId}")
    public String readPost(@PathVariable("postId") Long postId, Model model) {
        Post readPost = postService.readPost(postId);
        List<ReadCommentResponse> readComments = commentService.findCommentsByPostId(readPost.getId());
        model.addAttribute("post", readPost);
        model.addAttribute("comments", readComments);
        return "post/post";
    }

    // Update
    @GetMapping("/posts/{postId}/edit")
    public String getEditPost(@PathVariable Long postId, Model model){
        Post readPost = postService.readPost(postId);
        model.addAttribute("post", readPost);
        return "post/editPost";
    }
    @PostMapping("/posts/{postId}/edit")
    public String editPost(@PathVariable Long postId, Post post) {
        postService.editPost(postId, post);
        return "redirect:/posts/" + postId;
    }

    // Delete
    @DeleteMapping("/posts/{postId}")
    public void getDeletePost(@PathVariable Long postId) {
        postService.deletePost(postId);
    }

    // 파일 업로드 관련
    @GetMapping(value = "/posts/images/{fileId}",
            produces={MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public ResponseEntity<byte[]> getImage(@PathVariable Long fileId) throws IOException {
        byte[] image = storageService.getImage(fileId);
        return new ResponseEntity<>(image, HttpStatus.OK);
    }

}