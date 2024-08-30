package com.team8.teamproject.bookmark;

import com.team8.teamproject.board.controller.dto.MemberViewDto;
import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.bookmark.service.dto.BookmarkedBoardDto;
import com.team8.teamproject.common.SessionHandler;
import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.service.MemberService;
import com.team8.teamproject.oauth.dto.SessionUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    private final MemberService memberService;
    private final SessionHandler sessionHandler;


    //== 북마크 등록, 삭제 처리 ==//
    @ResponseBody
    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<Map<String, String>> clickBookmark(@PathVariable(value = "boardId") Long boardId, HttpServletRequest request) {

        MemberDto loggedInUser = sessionHandler.getSession(request);

    //구글 로그인 처리
        if (loggedInUser == null) {
        SessionUser sessionUser = sessionHandler.getGoogleLoggedInUser(request);
        loggedInUser = new MemberDto(sessionUser);
    }

    Map<String, String> data = bookmarkService.clickBookmark(loggedInUser.getId(), boardId);
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    /**
     * 북마크 삭제
     * 북마크 페이지에서는 삭제 알림을 띄어준 후 삭제를 진행
     * 게시판 페이지에서의 북마크 클릭(토글)과 달리 동작
     */
    @DeleteMapping("/bookmark/{boardId}")
    public ResponseEntity<Void> deleteBookmark(@PathVariable(value = "boardId") Long boardId, HttpServletRequest request) {

        MemberDto loggedInUser = sessionHandler.getSession(request);

        //구글 로그인 처리
        if (loggedInUser == null) {
            SessionUser sessionUser = sessionHandler.getGoogleLoggedInUser(request);
            loggedInUser = new MemberDto(sessionUser);
        }


        bookmarkService.deleteBookmark(loggedInUser.getId(), boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //== 북마크 페이지 VIEW ==//
    @GetMapping("/bookmark/{memberId}")
    public String getBookMarkPage(@PathVariable(value = "memberId") Long memberId, Model model) {

        List<BookmarkedBoardDto> bookmarkedViewDtos = bookmarkService.findBoardsViewDto(memberId);
        model.addAttribute("boards", bookmarkedViewDtos);

        //회원 정보 조회
        Member member = memberService.findById(memberId);


        model.addAttribute("member", new MemberViewDto(member));


        return "bookmark/bookmark";
    }

}
