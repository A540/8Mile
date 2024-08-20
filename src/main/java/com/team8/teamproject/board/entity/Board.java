package com.team8.teamproject.board.entity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    @OneToMany(mappedBy = "board")
//    private List<Post> posts = new ArrayList<>();

    private Board(String name, String description) {
        this.name = name;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }

//    // 연관 관계 편의 메서드
//    public linkUser(User user) {
//        this.user = user;
//        user.getBoards().add(this);
//    }

    //생성 메서드
    public static Board createBoard(String name, String description) {
        Board board = new Board(name, description);
        return board;
    }

    //게시판 수정
    public void update(String name, String description) {
        this.name = name;
        this.description =description;
        this.modifiedAt = LocalDateTime.now();
    }



}
