package com.team8.teamproject.board.controller;

import com.team8.teamproject.board.controller.dto.BoardForm;
import com.team8.teamproject.board.controller.dto.BoardsViewDto;
import com.team8.teamproject.board.controller.dto.UpdateBoardForm;
import com.team8.teamproject.board.entity.Board;
import com.team8.teamproject.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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

    //== 게시판 상세 ==//  //TODO
//    @GetMapping("/boards/{boardId}")
//    public String getBoard(Model model) {
//
//    }


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


}
