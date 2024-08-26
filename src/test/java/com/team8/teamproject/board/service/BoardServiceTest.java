package com.team8.teamproject.board.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.service.PostService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private EntityManager em;
    @Autowired
    private PostService postService;


    @Test
    public void 게시판등록() throws Exception {

        //given
        Board board = Board.createBoard("영화", "영화 리뷰");

        //when
        Long savedId = boardService.saveBoard(board);

        //then
        Board findBoard = boardRepository.findOne(savedId).get();
        assertThat(board).isEqualTo(findBoard);
    }

    @Test
    public void 게시판수정() throws Exception {

        //given
        Board board = Board.createBoard("액션 영화", "미션 임파서블");
        Long savedId = boardService.saveBoard(board);

        //when
        boardService.updateBoard(savedId, "액션 영화", "미션 임파서블777");
        Board updatedBoard = boardService.findBoard(savedId);

        //then
        assertThat(updatedBoard.getDescription()).isEqualTo("미션 임파서블777");
    }

    @Test  //부모 트랜잭션이 커밋되어야 자식 트랜잭션도 커밋
    public void 게시판삭제() throws Exception {

        //given
        Board board = Board.createBoard("SF", "어벤져스");
        Long savedId = boardService.saveBoard(board);

        //when
        boardService.deleteBoard(savedId);

        //then
        Board deltedBoard = boardRepository.findOne(savedId).get();
        assertThat(deltedBoard.isDeleted()).isTrue();

    }

    @Test
    public void 게시판의게시글() throws Exception {

        //given
        Board board = Board.createBoard("SF", "어벤져스");
        Long savedId = boardService.saveBoard(board);

        Board board2 = Board.createBoard("액션 영화", "미션 임파서블");
        Long savedId2 = boardService.saveBoard(board2);

//        //when
//        postService.createPost(savedId, "title1", "content1");
//        postService.createPost(savedId, "title2", "content2");
//
//        postService.createPost(savedId2, "title3", "content3");
//        postService.createPost(savedId2, "title4", "content4");

        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> posts = boardService.findPostsByBoardId(savedId2, " ", pageable);

        //then
        //첫번째 게시글의 title 확인
        assertThat(posts.getContent().get(0).getTitle()).isEqualTo("title3");

        //액션영화 게시판의 게시글은 총 2개
        assertThat(posts.getTotalElements()).isEqualTo(2);
    }
}