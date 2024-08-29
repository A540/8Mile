package com.team8.teamproject.bookmark;

import com.team8.teamproject.board.controller.dto.MemberViewDto;
import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.bookmark.service.dto.BookmarkedBoardDto;
import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.service.MemberService;
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


    //== 북마크 등록, 삭제 처리 ==//
    @ResponseBody
    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<Map<String, String>> clickBookmark(@PathVariable(value = "boardId") Long boardId, HttpServletRequest request) {

        // Spring Security에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDto loggedInUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            // OAuth2 로그인 사용자일 경우
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            Member user = memberService.findByEmail(email).orElse(null);

            loggedInUser = new MemberDto(user);

        } else {
            // 일반 로그인 사용자일 경우
            MemberDto memberDto = getSession(request);
            loggedInUser = memberDto;
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

        // Spring Security에서 현재 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        MemberDto loggedInUser = null;
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            // OAuth2 로그인 사용자일 경우
            OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
            String email = oAuth2User.getAttribute("email");
            Member user = memberService.findByEmail(email).orElse(null);

            loggedInUser = new MemberDto(user);

        } else {
            // 일반 로그인 사용자일 경우
            MemberDto memberDto = getSession(request);
            loggedInUser = memberDto;
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



    //== 세션로그인 멤버 정보 ==//
    private MemberDto getSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return null;
        }
        return (MemberDto) session.getAttribute("userDetails");
    }
}
