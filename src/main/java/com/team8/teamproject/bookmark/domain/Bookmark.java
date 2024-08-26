package com.team8.teamproject.bookmark.domain;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.login.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.awt.print.Book;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Bookmark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    //연관 관계 메서드
    public void linkMember(Member member) {
        this.member = member;
//        member.
    }

    public void linkBoard(Board board) {
        this.board = board;
    }

    public static Bookmark createBookmark(Member member, Board board) {
        Bookmark bookmark = new Bookmark();
        bookmark.linkMember(member);
        bookmark.linkBoard(board);
        return bookmark;
    }

}
