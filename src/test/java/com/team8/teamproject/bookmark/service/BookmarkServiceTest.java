package com.team8.teamproject.bookmark.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.service.BoardService;
import com.team8.teamproject.bookmark.domain.Bookmark;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback(value = false)
class BookmarkServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private BookmarkService bookmarkService;

    @Test
    public void 북마크등록() throws Exception {
        //given
        Member member1 = createMember("sss@naver.com", "sss", "111");
        Member member2 = createMember("ddd@naver.com", "ddd", "111");
        Member savedMember1 = memberService.save(member1);
        Member savedMember2 = memberService.save(member2);

        Board board1 = createBoard("액션", "액션 영화");
        Board board2 = createBoard("공포", "공포 영화");
        Board board3 = createBoard("SF", "SF 영화");
        Long boardId1 = boardService.saveBoard(board1);
        Long boardId2 = boardService.saveBoard(board2);
        Long boardId3 = boardService.saveBoard(board3);

        //when
        bookmarkService.clickBookmark(savedMember1.getId(), boardId1);
        bookmarkService.clickBookmark(savedMember1.getId(), boardId2);
        bookmarkService.clickBookmark(savedMember1.getId(), boardId3);

        //then
        List<Bookmark> bookmarkList = bookmarkService.findAllByMember(savedMember1.getId());
        assertThat(bookmarkList.size()).isEqualTo(3);
        assertThat(bookmarkList.get(1).getBoard().getName()).isEqualTo("공포");
    }

    @Test
    public void 북마크삭제() throws Exception {
        //given
        Member member1 = createMember("sss@naver.com", "sss", "111");
        Member savedMember1 = memberService.save(member1);

        Board board1 = createBoard("액션", "액션 영화");
        Long boardId1 = boardService.saveBoard(board1);

        bookmarkService.clickBookmark(savedMember1.getId(), boardId1);  //북마크 저장

        //when
        Map<String, String> deletedData = bookmarkService.clickBookmark(savedMember1.getId(), boardId1);

        //then
        List<Bookmark> bookmarkList = bookmarkService.findAllByMember(member1.getId());
        assertThat(bookmarkList.size()).isEqualTo(0);
        assertThat(deletedData.get("bookMarkStatus")).isEqualTo("unBookMark");

    }

    private Member createMember(String email, String userName, String password) {
        Member member = new Member();
        member.setUserName(userName);
        member.setEmail(email);
        member.setPassword(password);

        return member;
    }

    private Board createBoard(String name, String description) {
        return Board.createBoard(name, description);
    }
}