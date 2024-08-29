package com.team8.teamproject.board.controller;

import com.team8.teamproject.board.controller.dto.*;
import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNameDuplicateException;
import com.team8.teamproject.board.service.BoardService;
import com.team8.teamproject.bookmark.domain.Bookmark;
import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.login.controller.dto.MemberDto;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BookmarkService bookmarkService;

    @GetMapping
    public String getBoards(@RequestParam(value = "sort", required = false) String sort, Model model, HttpServletRequest request) {

        // 세션 정보 가져오기
        MemberDto loggedInUser = getSession(request);
        List<Long> bookMarkedBoardId = getBookMarkedBoardId(loggedInUser);
        if (loggedInUser != null) {
            model.addAttribute("member", new MemberViewDto(loggedInUser));
        }

        //게시판 정보
        List<Board> boards = (sort == null) ? boardService.findBoards() : boardService.findBoardsBySort(sort);

        //북마크 상태 저장 -> DTO 반환
        List<BoardsViewDto> boardsViewDtoList = boards.stream()
                .map(b -> {
                    BoardsViewDto dto = new BoardsViewDto(b);
                    dto.changeIsBookMarked(bookMarkedBoardId.contains(b.getId()));
                    return dto;
                })
                .collect(Collectors.toList());


        model.addAttribute("boards", boardsViewDtoList);
        return "board/boards";
    }

    //== 게시판 상세 ==//
    @GetMapping("/{boardId}")
    public String getBoard(@PathVariable(value = "boardId") Long boardId,
                           @RequestParam(value = "keyword", required = false) String keyword, Model model,
                           HttpSession session, Pageable pageable) {

        //게시판 정보
        Board board = boardService.findBoard(boardId);

        // 세션에서 조회 여부 확인
        String sessionKey = "viewedBoard_" + boardId;
        if (session.getAttribute(sessionKey) == null) {
            // 세션에 조회 기록이 없으면 조회수 증가
            boardService.updateViewCount(board);
            session.setAttribute(sessionKey, true);  // 세션에 조회 기록 추가
        }

        BoardViewDto boardViewDto = new BoardViewDto(board);

        //게시글 정보 (keyword 검색)
        Page<Post> postPage = boardService.findPostsByBoardId(boardId, keyword, pageable);
        Page<PostPageDto> postPageDtos = postPage.map(PostPageDto::new);

        model.addAttribute("board", boardViewDto);
        model.addAttribute("postPage", postPageDtos);

        return "board/board";
    }

    //== 게시판 생성 ==//
    @GetMapping("/create")
    public String createBoardForm(Model model) {

        model.addAttribute("boardForm", new BoardForm());
        return "board/createBoard";
    }

    @PostMapping("/create")
    public String createBoard(@Valid @ModelAttribute BoardForm boardForm, BindingResult bindingResult) {

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
    @GetMapping("/{boardId}/edit")
    public String updateBoardForm(@PathVariable(value = "boardId") Long boardId, Model model) {
        Board updateBoard = boardService.findBoard(boardId);
        UpdateBoardForm boardForm = new UpdateBoardForm(boardId, updateBoard.getName(), updateBoard.getDescription());
        model.addAttribute("board", boardForm);

        return "board/editBoard";
    }

    @PostMapping("/{boardId}/edit")
    public String updateBoard(@PathVariable(value = "boardId") Long boardId, @ModelAttribute UpdateBoardForm boardForm) {
        boardService.updateBoard(boardId, boardForm.getName(), boardForm.getDescription());
        return "redirect:/boards";
    }

    @DeleteMapping("/{boardId}/delete")
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

    //== 세션 로그인 멤버 정보 ==//
    private MemberDto getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return (session != null) ? (MemberDto) session.getAttribute("userDetails") : null;
    }


    //회원의 북마크된 게시판 아이디 리스트 ==//
    private List<Long> getBookMarkedBoardId(MemberDto loggedInUser) {
        return (loggedInUser != null) ? bookmarkService.getBookMarkedBoardId(loggedInUser.getId()) : new ArrayList<>();
    }


}
