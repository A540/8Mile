package com.team8.teamproject.post.storage;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.domain.Files;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.dto.FileDTO;
import com.team8.teamproject.post.repository.FileRepository;
import com.team8.teamproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;

@Service
@RequiredArgsConstructor
public class StorageLocalImpl implements StorageService {

    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;

    // Create
    @Transactional(readOnly = true)
    public void createLocalPost(Long boardId, String title, String content, MultipartFile file, Member member) throws IOException {
        Board board = boardRepository.findOne(boardId).orElseThrow(IllegalArgumentException::new);

        // 파일 업르도 관련
        String filename = file.getOriginalFilename();
        String savePath = "C:\\upload\\files"; // 경로: c:\\upload\\files

        // 경로에 폴더가 없다면 생성
        if (!new java.io.File(savePath).exists()) {
            try{
                new File(savePath).mkdirs();
            }
            catch(Exception e){
                e.getStackTrace();
            }
        }

        String filePath = savePath + "\\" + filename;

        file.transferTo(new File(filePath));

        FileDTO fileDTO = new FileDTO();
        fileDTO.setFilename(filename);
        fileDTO.setFilepath(filePath);

        Long fileId = saveFile(fileDTO);

        Post savePost = new Post(board, title, content, fileId, member);
        postRepository.save(savePost);
    }

    // 파일 업로드 관련
    @Transactional(readOnly = true)
    public Long saveFile(FileDTO fileDTO) {
        return fileRepository.save(fileDTO.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public FileDTO getFile(Long id) {
        Files file = fileRepository.findById(id).get();

        FileDTO fileDTO = FileDTO.builder()
                .id(id)
                .filename(file.getFilename())
                .filepath(file.getFilePath())
                .build();
        return fileDTO;
    }

    @Transactional(readOnly = true)
    public byte[] getImage(Long fileId) throws IOException {
        FileDTO fileDTO = getFile(fileId);
        InputStream inputStream = new FileInputStream(fileDTO.getFilepath());
        byte[] image = IOUtils.toByteArray(inputStream);
        inputStream.close();
        return image;
    }
}