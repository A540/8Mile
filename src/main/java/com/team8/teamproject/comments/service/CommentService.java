package com.team8.teamproject.comments.service;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.mapper.CommentMapper;
import com.team8.teamproject.comments.repository.CommentRepository;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.repository.PostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;



    public List<Comments> findAll(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not Found" + id));
        return commentRepository.findByPost(post);
    }

    public Comments save(long id, String content, Member member) {
        Post basePost = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Not Found Post" + id));
        Comments comment = new Comments(content, basePost, member);
        return commentRepository.save(comment);
    }

//    public Comments findById(Long id){
//        return commentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
//    }

    @Transactional      // 메소드 수행중 에러 발생 시 수행하기 이전 트랜젝션으로 복구
    public Comments update(long id, String content){
        Comments target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found comment" + id));

        target.updateContent(content);

        return target;
    }

    // id를 갖는 comment 삭제
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
