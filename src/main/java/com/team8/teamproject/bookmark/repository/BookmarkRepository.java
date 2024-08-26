package com.team8.teamproject.bookmark.repository;


import com.team8.teamproject.bookmark.domain.Bookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    List<Bookmark> findAllByMemberId(Long memberId);
}
