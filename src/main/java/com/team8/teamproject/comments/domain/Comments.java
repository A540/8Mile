package com.team8.teamproject.comments.domain;

import com.team8.teamproject.file.UploadFile;
import com.team8.teamproject.login.entity.Member;
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
    private Long id;    // commentId

    @Column(name = "content", nullable = false)
    private String content;     // 댓글 내용

    @Column(name = "likeCount")
    private int likeCount;      // 좋아요 개수

    @Column(name = "storeFileName")
    private String storeFileName;      // 댓글에 사용된 이미지

    @CreatedDate //엔티티 생성시 생성 시간 저장
    @Column(name = "createdAt")
    private LocalDateTime createdAt;

    @LastModifiedDate //엔티티가 수정될 때 수정 시간 저장
    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "postId")
    private Post post;          // 댓글이 위치한 게시글

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;      // 댓글을 작성한 유저

    public Comments(String content, Post post, Member member, String storeFileName){
        this.content = content;
        this.post = post;
        this.member = member;
        this.storeFileName = storeFileName;
    }

    public Comments(String content, Post post, Member member) {
        this.content = content;
        this.post = post;
        this.member = member;
    }

    public void updateContent(String content){
        this.content = content;
    }

    public void addLikeCount(){
        this.likeCount++;
    }

//    게시물 내 댓글 개수?
//    public int size(){}
}
