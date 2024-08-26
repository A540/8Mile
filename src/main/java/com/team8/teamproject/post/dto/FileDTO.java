package com.team8.teamproject.post.dto;

import com.team8.teamproject.post.domain.Files;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FileDTO {
    private Long id;
    private String filename;
    private String filePath;

    public Files toEntity () {
        Files file = Files.builder()
                    .id(id)
                    .filename(filename)
                    .filePath(filePath)
                    .build();
        return file;
    }

    @Builder
    public FileDTO(Long id, String filename, String filePath) {
        this.id = id;
        this.filename = filename;
        this.filePath = filePath;
    }
}
