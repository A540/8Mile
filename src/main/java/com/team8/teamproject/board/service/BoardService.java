package com.team8.teamproject.board.service;

import com.team8.teamproject.board.entity.Board;
import com.team8.teamproject.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long saveBoard(Board board) {
        boardRepository.save(board);
        return board.getId();
    }

    public Board findBoard(Long boardId) {
        return boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
    }

    public List<Board> findBoards() {
        return boardRepository.findAll();
    }

    @Transactional
    public void deleteBoard(Long boardId) {
        boardRepository.deleteOne(boardId);
    }

    @Transactional
    public void updateBoard(Long boardId, String name, String description) {

        //Dirty check
        Board updateBoard = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        updateBoard.update(name, description);

    }

}
