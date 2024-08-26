package com.team8.teamproject.post.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Files {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String filename;

    @Column(nullable = false)
    private String filePath;

    public Files toEntity() {
        Files file = Files.builder()
                .id(id)
                .filename(filename)
                .filePath(filePath)
                .build();
        return file;
    }

    @Builder
    public Files(Long id, String filename, String filePath) {
        this.id = id;
        this.filename = filename;
        this.filePath = filePath;
    }
}
