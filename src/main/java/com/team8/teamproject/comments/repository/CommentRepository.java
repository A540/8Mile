package com.team8.teamproject.comments.repository;

import com.team8.teamproject.comments.domain.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comments, Long> {
}
