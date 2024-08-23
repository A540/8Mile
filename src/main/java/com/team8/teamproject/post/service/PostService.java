package com.team8.teamproject.post.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.comments.repository.CommentRepository;
import com.team8.teamproject.post.domain.Files;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.dto.FileDTO;
import com.team8.teamproject.post.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import com.team8.teamproject.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {
    // 데이터베이스
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 파일 업로드
    private final FileRepository fileRepository;

    // 게시글 상세 조회
    public List<Comments> readComment(Post post) {
        List<Comments> readPost = commentRepository.findByPost(post);
        return readPost;
    }
    
    // Create
    public void createPost(Long boardId, String title, String content, MultipartFile file) throws IOException {
        Board board = boardRepository.findOne(boardId).orElseThrow(IllegalArgumentException::new);

        // 파일 업르도 관련
        String filename = file.getOriginalFilename();
        String savePath = "c:\\upload" + "\\files"; // Path: c:\\upload\\files

        // 경로에 폴더가 없다면 생성
        if (!new java.io.File(savePath).exists()) {
            try{
                new java.io.File(savePath).mkdirs();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        String filePath = savePath + "\\" + filename;

        file.transferTo(new java.io.File(filePath));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(filename);
        fileDTO.setFilePath(filePath);

        Long fileId = saveFile(fileDTO);

        Post savePost = new Post(board, title, content, fileId);
        postRepository.save(savePost);
    }

    // Read
    public Post readPost(Long postId) {
        return postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);
    }

    // Update
    public void editPost(Long postId, Post post) {
        Post updatePost = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        updatePost.setTitle(post.getTitle());
        updatePost.setContent(post.getContent());

        postRepository.save(updatePost);
    }

    // Delete
    public void deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(IllegalArgumentException::new);

        postRepository.delete(post);
    }

    // 파일 업로드 관련
    public Long saveFile(FileDTO fileDTO) {
        return fileRepository.save(fileDTO.toEntity()).getId();
    }

    public FileDTO getFile(Long id) {
        Files file = fileRepository.findById(id).get();

        FileDTO fileDTO = FileDTO.builder()
                .id(id)
                .filename(file.getFilename())
                .filePath(file.getFilePath())
                .build();
        return fileDTO;
    }
}