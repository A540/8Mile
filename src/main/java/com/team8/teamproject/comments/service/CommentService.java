package com.team8.teamproject.comments.service;

import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.dto.ReadCommentResponse;
import com.team8.teamproject.comments.exception.CommentNotFoundException;
import com.team8.teamproject.comments.exception.PostNotFoundException;
import com.team8.teamproject.comments.mapper.CommentMapper;
import com.team8.teamproject.comments.repository.CommentRepository;
import com.team8.teamproject.file.FileStore;
import com.team8.teamproject.file.UploadFile;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.repository.PostRepository;
import com.team8.teamproject.post.storage.StorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private final CommentMapper commentMapper;

    private final FileStore fileStore;
    private final StorageService storageService;



    public List<ReadCommentResponse> findCommentsByPostId(long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(PostNotFoundException::new);
        return commentMapper.commentsToResponses(commentRepository.findByPost(post));
    }

    @Transactional
    public Comments saveComment(long id, String content, Member member, MultipartFile file) throws IOException {
        Post basePost = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Comments comment;
        UploadFile uploadFile = fileStore.storeFile(file);
        if(uploadFile == null){
            comment = new Comments(content, basePost, member);
        }
        else{
            comment = new Comments(content, basePost, member, uploadFile.getStoreFileName());
        }

        return commentRepository.save(comment);
    }

//    public Comments findById(Long id){
//        return commentRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("not found" + id));
//    }

    @Transactional      // 메소드 수행중 에러 발생 시 수행하기 이전 트랜젝션으로 복구
    public Comments update(long id, String content){
        Comments target = commentRepository.findById(id)
                .orElseThrow(CommentNotFoundException::new);

        target.updateContent(content);

        return target;
    }

    @Transactional      // id를 갖는 comment 삭제
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }


    @Transactional
    public void addLike(Long id) {
        Comments comments = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        comments.addLikeCount();
    }

    public byte[] getCommentImage(String filename) throws IOException {
        String filePath = fileStore.getFullPath(filename);
        return storageService.getImageByFileName(filePath);
    }
}
