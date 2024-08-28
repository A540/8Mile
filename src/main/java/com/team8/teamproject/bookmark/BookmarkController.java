package com.team8.teamproject.bookmark;

import com.team8.teamproject.board.controller.dto.BoardsViewDto;
import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.bookmark.domain.Bookmark;
import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.login.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;

    //== 북마크 등록, 삭제 처리 ==//
    @ResponseBody
    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<Map<String, String>> clickBookmark(@PathVariable(value = "boardId") Long boardId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            log.info("no user");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        Map<String, String> data = bookmarkService.clickBookmark(loggedInUser.getId(), boardId);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    //== 북마크 페이지 VIEW ==//
    @GetMapping("/bookmark/{memberId}")
    public ResponseEntity<List<BoardsViewDto>> getBookMarkPage(@PathVariable(value = "memberId") Long memberId, HttpServletRequest request) {

        List<Bookmark> bookmarkList = bookmarkService.findAllByMember(memberId);

        //북마크된 게시판 리스트
        List<Board> boardList = bookmarkList.stream().
                map(Bookmark::getBoard)
                .collect(Collectors.toList());

        //북마크된 게시판 IDs
        List<Long> bookMarkedBoardId = bookmarkList.stream()
                .map(b -> b.getBoard().getId())
                .collect(Collectors.toList());


        List<BoardsViewDto> boardsViewDtos = boardList.stream()
                .map(b -> {
                    BoardsViewDto dto = new BoardsViewDto(b);
                    dto.changeIsBookMarked(bookMarkedBoardId.contains(b.getId()));
                    return dto;
                })
                .collect(Collectors.toList());


        return new ResponseEntity<>(boardsViewDtos, HttpStatus.OK);
    }
}
