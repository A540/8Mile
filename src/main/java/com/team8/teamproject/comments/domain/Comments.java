package com.team8.teamproject.comments.domain;

import com.team8.teamproject.post.domain.Post;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Comments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    @CreatedDate //엔티티 생성시 생성 시간 저장
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate //엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

//    @Column(name="postId")
//    private Long postId;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;

    public void update(String content){
        this.content = content;
    }


//    게시물 내 댓글 개수?
//    public int size(){}
}
