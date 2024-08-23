package com.team8.teamproject.board.domain;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime modifiedAt;
    private boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "board")
    private List<Post> posts = new ArrayList<>();

    private Board(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // 연관 관계 편의 메서드 TODO
    public void linkUser(Member member) {
        this.member = member;
//        member.getBoards().add(this);
    }

    //생성 메서드
    public static Board createBoard(String name, String description) {
        Board board = new Board(name, description);
        return board;
    }

    //게시판 수정
    public void updateBoard(String name, String description) {
        this.name = name;
        this.description =description;
    }

    //게시판 삭제 (soft delete)
    public void deleteBoard() {
        this.isDeleted = true;
    }



}
