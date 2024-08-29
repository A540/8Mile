package com.team8.teamproject.board.controller;

import com.team8.teamproject.board.controller.dto.*;
import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNameDuplicateException;
import com.team8.teamproject.board.service.BoardService;
import com.team8.teamproject.bookmark.domain.Bookmark;
import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.oauth.dto.PrincipalDetails;
import com.team8.teamproject.post.domain.Post;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;
    private final BookmarkService bookmarkService;

//    @GetMapping
//    public String getBoards(@RequestParam(value = "sort", required = false) String sort,
//                            @AuthenticationPrincipal PrincipalDetails principalDetails,
//                            Model model) {
//
//        // PrincipalDetails를 통해 현재 로그인된 사용자 정보를 가져옴
//        MemberDto loggedInUser = principalDetails != null ? principalDetails.getMember() : null;
//
//        if (loggedInUser != null) {
//            model.addAttribute("member", new MemberViewDto(loggedInUser));
//        }
//
//        // 게시판 정보 가져오기
//        List<Board> boards = (sort == null) ? boardService.findBoards() : boardService.findBoardsBySort(sort);
//
//        // 북마크 상태 저장 -> DTO 반환
//        List<BoardsViewDto> boardsViewDtoList = boards.stream()
//                .map(b -> {
//                    BoardsViewDto dto = new BoardsViewDto(b);
//                    dto.changeIsBookMarked(getBookMarkedBoardId(loggedInUser).contains(b.getId()));
//                    return dto;
//                })
//                .collect(Collectors.toList());
//
//        model.addAttribute("boards", boardsViewDtoList);
//        return "board/boards";
//    }

    //== 게시판 상세 ==//
    @GetMapping
    public String getBoards(@RequestParam(value = "sort", required = false) String sort,
                            Model model, HttpServletRequest request,
                            @AuthenticationPrincipal UserDetails userDetails) {

        // 세션 정보를 통해 로그인된 사용자 정보 가져오기
        MemberDto loggedInUser = getSession(request);

        // 만약 세션에서 사용자를 찾지 못하면 UserDetails에서 사용자 정보를 가져옵니다.
        if (loggedInUser == null && userDetails != null) {
            if (userDetails instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) userDetails;
                loggedInUser = convertOAuth2UserToMemberDto(oAuth2User); // OAuth2 로그인 사용자 처리
            } else if (userDetails instanceof PrincipalDetails) {
                PrincipalDetails principalDetails = (PrincipalDetails) userDetails;
                loggedInUser = principalDetails.getMember(); // 일반 로그인 사용자 처리
            }
        }

        List<Long> bookMarkedBoardId = getBookMarkedBoardId(loggedInUser);

        if (loggedInUser != null) {
            model.addAttribute("member", new MemberViewDto(loggedInUser));
        }

        // 게시판 정보 가져오기
        List<Board> boards = (sort == null) ? boardService.findBoards() : boardService.findBoardsBySort(sort);

        // 북마크 상태 저장 -> DTO 반환
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

    // 세션에서 사용자 정보를 가져오는 메서드

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

    private MemberDto convertOAuth2UserToMemberDto(OAuth2User oAuth2User) {
        // OAuth2User에서 제공하는 기본 속성 추출
        String userName = null;
        String email = null;
        Long id = null;

        // OAuth2User에서 제공하는 provider별 속성 가져오기
        String registrationId = oAuth2User.getAttribute("registration_id");  // provider 구분
        Map<String, Object> attributes = oAuth2User.getAttributes();

        return new MemberDto(id, userName, email);
    }
}
