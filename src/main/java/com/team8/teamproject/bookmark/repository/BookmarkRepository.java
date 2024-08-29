package com.team8.teamproject.bookmark.repository;


import com.team8.teamproject.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    @Query("select b from Bookmark b join fetch b.board where b.member.id = :memberId")
    List<Bookmark> findAllByMemberId(Long memberId);

    @Query("select b.board.id from Bookmark b where b.member.id = :memberId")
    List<Long> findBookMarkedBoardIds(Long memberId);

    //북마크 상태 return  -> 1이상이면 북마크 체크 상태
    @Query("select count(b) from Bookmark b where b.member.id = :memberId and b.board.id = :boardId")
    int getBookmarkCount(Long memberId, Long boardId);

    @Modifying
    @Query("delete from Bookmark b where b.member.id = :memberId and b.board.id = :boardId ")
    void deleteBookmark(Long memberId, Long boardId);

    @Modifying
    @Query("delete from Bookmark b where b.board.id = :boardId")
    void deleteBookmarkByBoardId(Long boardId);


}
