package com.team8.teamproject.post.repository;

import com.team8.teamproject.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p "+
            " where p.board.id = :boardId" +
            " and (:keyword is null or :keyword = ' '" +
            " or p.title like %:keyword% or p.content like %:keyword%)")
    Page<Post> findAllByBoardIdKeyword(@Param("boardId") Long boardId, @Param("keyword") String keyword, @Param("pageable") Pageable pageable);
}
