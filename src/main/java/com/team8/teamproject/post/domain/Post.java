package com.team8.teamproject.post.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class) // createdAt, modifiedAt
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;

/*  연관관계 설정
    @ManyToOne
    @JoinColumn(name = "boardId")
    Board board;

    @OneToMany
    @JoinColumn(name = "commentId")
    Comments comment;

    @ManyToOne
    @JoinColumn(name = "userId")
    User user;
*/
}


