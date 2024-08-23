package com.team8.teamproject.board.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNameDuplicateException;
import com.team8.teamproject.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long saveBoard(Board board) {

        try {
            boardRepository.save(board);
        } catch (DataIntegrityViolationException e) {  //중복 게시판 검증
            throw new BoardNameDuplicateException("이미 존재하는 게시판명입니다.");
        }

        return board.getId();
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
    }

    public List<Board> findBoards() {
        return boardRepository.findALlByIsDeletedFalse();
    }


    @Transactional
    public void updateBoard(Long boardId, String name, String description) {

        //Dirty check
        Board updateBoard = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        updateBoard.updateBoard(name, description);
    }

//    @Transactional
//    public void deleteBoard(Long boardId) {
//        Board deleteBoard = boardRepository.findOne(boardId).
//                orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
//
//        boardRepository.deleteOne(deleteBoard);
//    }

    //== 게시판 soft delete ==//
    @Transactional
    public void deleteBoard(Long boardId) {
        Board deleteBoard = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제된 게시판입니다."));

        //dirty check
        deleteBoard.deleteBoard();
    }

}
