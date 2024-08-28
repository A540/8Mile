package com.team8.teamproject.post.storage;

import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.dto.FileDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface StorageService {
    void createLocalPost(Long boardId, String title, String content, MultipartFile file, Member member, HttpServletRequest request) throws IOException;
    Long saveFile(FileDTO fileDTO);
    FileDTO getFile(Long id);
    byte[] getImage(Long fileId) throws IOException;
}
