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
    private String filepath;

    public Files toEntity () {
        Files file = Files.builder()
                    .id(id)
                    .filename(filename)
                    .filePath(filepath)
                    .build();
        return file;
    }

    @Builder
    public FileDTO(Long id, String filename, String filepath) {
        this.id = id;
        this.filename = filename;
        this.filepath = filepath;
    }
}
