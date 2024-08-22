package com.team8.teamproject.board.controller;

import com.team8.teamproject.board.controller.dto.*;
import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.service.BoardService;
import com.team8.teamproject.post.domain.Post;
import com.team8.teamproject.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final PostRepository postRepository;

    //== 게시판 목록 ==//
    @GetMapping("/boards")
    public String getBoards(Model model) {
        List<Board> boards = boardService.findBoards();

        List<BoardsViewDto> boardsViewDtoList = boards.stream()
                .map(BoardsViewDto::new)
                .collect(Collectors.toList());
        model.addAttribute("boards", boardsViewDtoList);

        return "board/boards";
    }

    //== 게시판 상세 ==//
    @GetMapping("/boards/{boardId}")
    public String getBoard(@PathVariable(value = "boardId") Long boardId, Model model, Pageable pageable) {

        //게시판 정보
        Board board = boardService.findBoard(boardId);
        BoardViewDto boardViewDto = new BoardViewDto(board);

        //게시글 정보
        List<Post> posts = board.getPosts();
        Page<Post> postPage = postRepository.findAll(pageable);
        Page<PostPageDto> postPageDtos = postPage.map(PostPageDto::new);


        model.addAttribute("board", boardViewDto);
        model.addAttribute("postPage", postPageDtos);

        return "board/board";

    }


    //== 게시판 생성 ==//
    @GetMapping("/boards/create")
    public String createBoardForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());
        return "board/createBoard";
    }

    @PostMapping("/boards/create")
    public String createBoard(@ModelAttribute BoardForm boardForm) {
        Board newBoard = Board.createBoard(boardForm.getName(), boardForm.getDescription());
        boardService.saveBoard(newBoard);

        return "redirect:/boards";
    }

    //== 게시판 수정 ==//
    @GetMapping("/boards/{boardId}/edit")
    public String updateBoardForm(@PathVariable(value = "boardId") Long boardId, Model model) {
        Board updateBoard = boardService.findBoard(boardId);
        UpdateBoardForm boardForm = new UpdateBoardForm(boardId, updateBoard.getName(), updateBoard.getDescription());
        model.addAttribute("board", boardForm);

        return "board/editBoard";
    }

    @PostMapping("/boards/{boardId}/edit")
    public String updateBoard(@PathVariable(value = "boardId") Long boardId, @ModelAttribute UpdateBoardForm boardForm) {
        boardService.updateBoard(boardId, boardForm.getName(), boardForm.getDescription());
        return "redirect:/boards";
    }

    @DeleteMapping("/boards/{boardId}/delete")
    public String deleteBoard(@PathVariable(value = "boardId") Long boardId) {
        boardService.deleteBoard(boardId);

        return "board/boards";
    }


}
