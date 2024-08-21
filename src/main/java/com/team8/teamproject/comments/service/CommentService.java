package com.team8.teamproject.comments.service;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.AddCommentRequest;
import com.team8.teamproject.comments.mapper.CommentMapper;
import com.team8.teamproject.comments.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;


    public List<Comments> findAll(Long id) {
        return commentRepository.findByPostId(id);
    }

    public Comments save(long id, AddCommentRequest request) {
        Comments comment = commentMapper.toEntity(request);
        comment.setPostId(id);
        return commentRepository.save(comment);
    }

//    public Comments findById(Long id){
//        return commentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
//    }

    @Transactional      // 메소드 수행중 에러 발생 시 수행하기 이전 트랜젝션으로 복구
    public Comments update(long id, AddCommentRequest request){
        Comments target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found" + id));

        target.update(request.getContent());

        return target;
    }

    // id를 갖는 comment 삭제
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
