package com.team8.teamproject.bookmark;

import com.team8.teamproject.bookmark.service.BookmarkService;
import com.team8.teamproject.login.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;


    @PostMapping("/bookmark/{boardId}")
    public ResponseEntity<Map<String,String>> clickBookmark(@PathVariable(value = "boardId") Long boardId, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUser") == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Member loggedInUser = (Member) session.getAttribute("loggedInUser");
        Map<String, String> data = bookmarkService.clickBookmark(loggedInUser.getId(), boardId);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
