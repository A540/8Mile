package com.team8.teamproject.board.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNameDuplicateException;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardService {

    private final BoardRepository boardRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveBoard(Board board) {

        try {
            boardRepository.save(board);
        } catch (DataIntegrityViolationException e) {  //중복 게시판 검증
            throw new BoardNameDuplicateException("이미 존재하는 게시판명입니다.");
        }

        return board.getId();
    }

    @Transactional
    public Board findBoard(Long boardId) {
        Board board = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));
        board.updateViewCount(board.getViewCount() + 1);  //조회수 업데이트

        return board;
    }

    public List<Board> findBoards() {
        return boardRepository.findALlByIsDeletedFalse();
    }

    //단일 책임 원칙 위배 -> 테스트 후 PostService로 이전 TODO
    public Page<Post> findPostsByBoardId(Long boardId, String keyword, Pageable pageable) {
        return postRepository.findAllByBoardIdKeyword(boardId, keyword, pageable);
    }


    @Transactional
    public void updateBoard(Long boardId, String name, String description) {

        //Dirty check
        Board updateBoard = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시판이 존재하지 않습니다."));

        updateBoard.updateBoard(name, description);
    }

    //== 게시판 soft delete ==//
    @Transactional
    public void deleteBoard(Long boardId) {
        Board deleteBoard = boardRepository.findOne(boardId)
                .orElseThrow(() -> new IllegalArgumentException("이미 삭제된 게시판입니다."));

        //dirty check
        deleteBoard.deleteBoard();
    }

}
