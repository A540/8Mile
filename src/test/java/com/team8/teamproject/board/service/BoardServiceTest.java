package com.team8.teamproject.board.service;

import com.team8.teamproject.board.entity.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;


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
}