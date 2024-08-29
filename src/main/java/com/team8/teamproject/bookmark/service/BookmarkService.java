package com.team8.teamproject.bookmark.service;

import com.team8.teamproject.board.domain.Board;
import com.team8.teamproject.board.exception.BoardNotFoundException;
import com.team8.teamproject.board.repository.BoardRepository;
import com.team8.teamproject.bookmark.domain.Bookmark;
import com.team8.teamproject.bookmark.repository.BookmarkRepository;
import com.team8.teamproject.bookmark.service.dto.BookmarkedBoardDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.exception.MemberNotFoundException;
import com.team8.teamproject.login.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;


    //== 북마크 추가 or 북마크 삭제 ==//
    @Transactional
    public Map<String, String> clickBookmark(Long memberId, Long boardId) {

        Map<String, String> data = new HashMap<>();

        Member member = memberRepository.findById(memberId).
                orElseThrow(() -> new MemberNotFoundException("회원이 존재하지 않습니다."));
        Board board = boardRepository.findOne(boardId)
                        .orElseThrow(() ->  new BoardNotFoundException("게시판이 존재하지 않습니다."));


        int bookmarkCount = bookmarkRepository.getBookmarkCount(memberId, boardId);
        if (bookmarkCount == 0) {
            Bookmark bookmark = Bookmark.createBookmark(member, board);
            bookmarkRepository.save(bookmark);
            data.put("bookMarkStatus", "bookMark");
        } else {
            bookmarkRepository.deleteBookmark(memberId, boardId);
            data.put("bookMarkStatus", "unBookMark");
        }

        return data;
    }

    //북마크된 게시판 아이디 리턴
    public List<Long> getBookMarkedBoardId(Long memberId) {
        return bookmarkRepository.findBookMarkedBoardIds(memberId);
    }


    public List<Bookmark> findAllByMember(Long memberId) {
        return bookmarkRepository.findAllByMemberId(memberId);
    }

    @Transactional
    public void deleteBookmark(Long memberId, Long boardId) {
        bookmarkRepository.deleteBookmark(memberId, boardId);
    }


    //== 북마크 VIEW에 맞는 DTO 리턴 ==//
    public List<BookmarkedBoardDto> findBoardsViewDto(Long memberId) {

        List<Bookmark> bookmarkList = bookmarkRepository.findAllByMemberId(memberId);

        //북마크된 게시판 리스트
        List<Board> boardList = bookmarkList.stream()
                .map(Bookmark::getBoard)
                .collect(Collectors.toList());

        //북마크된 게시판 IDs
        List<Long> bookMarkedBoardId = bookmarkList.stream()
                .map(b -> b.getBoard().getId())
                .collect(Collectors.toList());

//        boardList.stream()
//                .map(Board::getId)
//                .collect(Collectors.toList())

        List<BookmarkedBoardDto> boardsViewDtos = boardList.stream()
                .map(b -> {
                    BookmarkedBoardDto dto = new BookmarkedBoardDto(b);
                    dto.changeIsBookMarked(bookMarkedBoardId.contains(b.getId()));
                    return dto;
                })
                .collect(Collectors.toList());

        return boardsViewDtos;
    }


}
