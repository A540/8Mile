package com.team8.teamproject.post.repository;

import com.team8.teamproject.post.domain.Rating;
import com.team8.teamproject.post.domain.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {
    @Query(value = "SELECT AVG(rating) FROM rating r WHERE r.post_id = :postId", nativeQuery = true)
    Double findAvgRating(@Param(value = "postId") Long postId);

    @Query(value = "SELECT COUNT(*) FROM rating r WHERE r.post_id = :postId", nativeQuery = true)
    Integer findCountRating(@Param(value = "postId") Long postId);
}
