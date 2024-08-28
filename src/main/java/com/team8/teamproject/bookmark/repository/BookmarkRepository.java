package com.team8.teamproject.bookmark.repository;


import com.team8.teamproject.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByMemberId(Long memberId);

    Optional<Bookmark> findAllByMemberIdAndBoardId(Long memberId, Long boardID);

    //북마크 상태 return  -> 1이상이면 북마크 체크 상태
    @Query("select count(b) from Bookmark b where b.member.id = :memberId and b.board.id = :boardId")
    int getBookmarkCount(Long memberId, Long boardId);

    @Modifying
    @Query("delete from Bookmark b where b.member.id = :memberId and b.board.id = :boardId ")
    void deleteBookmark(Long memberId, Long boardId);
}
