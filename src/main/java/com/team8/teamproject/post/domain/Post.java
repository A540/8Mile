package com.team8.teamproject.post.domain;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.comments.domain.Comments;
import com.team8.teamproject.login.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // 파일 업로드 관련
    private Long fileId;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<Comments> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "board_Id")
    Board board;


    @ManyToOne
    @JoinColumn(name = "userId")
    Member member;

    public Post(){

    }
    // BoardId를 포함하는 생성자
    public Post(Board board, String title, String content, Long fileId, Member member){
        this.board = board;
        this.title = title;
        this.content = content;
        this.fileId = fileId;
        this.member = member;
    }

    public Post(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}


