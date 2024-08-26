package com.team8.teamproject.board.controller;

import com.team8.teamproject.board.controller.dto.*;
import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNameDuplicateException;
import com.team8.teamproject.board.service.BoardService;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.post.domain.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //== 게시판 목록 ==//
    @GetMapping("/boards")
    public String getBoards(@RequestParam(value = "sort", required = false) String sort, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {

        //session 정보 가져오기
        HttpSession session = request.getSession(false);

        MemberViewDto memberViewDto = null;
        if (session != null && session.getAttribute("loggedInUser") != null) {
            Member loggedInUser = (Member) session.getAttribute("loggedInUser");
            memberViewDto = new MemberViewDto(loggedInUser);
        } else {
            log.info("비회원 호출");
        }
        model.addAttribute("member", memberViewDto);


        //게시판 정보
        List<Board> boards = null;
        if(sort == null) {  //정렬 정보가 없으면 기본값
            boards = boardService.findBoards();
        }
        else {
            boards = boardService.findBoardsBySort(sort);
        }

        List<BoardsViewDto> boardsViewDtoList = boards.stream()
                .map(BoardsViewDto::new)
                .collect(Collectors.toList());
        model.addAttribute("boards", boardsViewDtoList);

        return "board/boards";
    }

    //== 게시판 상세 ==//
    @GetMapping("/boards/{boardId}")
    public String getBoard(@PathVariable(value = "boardId") Long boardId,
                           @RequestParam(value = "keyword", required = false) String keyword, Model model, Pageable pageable) {

        //게시판 정보
        Board board = boardService.findBoard(boardId);
        BoardViewDto boardViewDto = new BoardViewDto(board);

        //게시글 정보 (keyword 검색)
        Page<Post> postPage = boardService.findPostsByBoardId(boardId, keyword, pageable);
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
    public String createBoard(@Valid @ModelAttribute BoardForm boardForm, BindingResult bindingResult) {

        //게시판 이름이나 설명을 잘못 적으면 (특수문자, 공백 등)
        if (bindingResult.hasErrors()) {
            log.info("error={}", bindingResult.getObjectName());
            return "board/createBoard";
        }

        //중복 게시판 검증
        try {
            Board newBoard = Board.createBoard(boardForm.getName(), boardForm.getDescription());
            boardService.saveBoard(newBoard);
        } catch (BoardNameDuplicateException e) {
            throw e;
        }

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

    //중복 예외 처리
    @ExceptionHandler(BoardNameDuplicateException.class)
    public String BoardDuplicateExceptionHandler(BoardNameDuplicateException e, Model model, RedirectAttributes redirectAttributes) {
        log.error("[BoardNameDuplicateException] ex", e.getMessage());

        redirectAttributes.addFlashAttribute("boardForm", new BoardForm());
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return "redirect:/boards/create";
    }

}
