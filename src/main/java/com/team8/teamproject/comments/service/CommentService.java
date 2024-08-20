package com.team8.teamproject.comments.service;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.mapper.CommentMapper;
import com.team8.teamproject.comments.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public Comments save(Long id, AddCommentRequest request) {
        Comments comment = commentMapper.toEntity(request);
        comment.setPostId(id);
        return commentRepository.save(comment);
    }

}
