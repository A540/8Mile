package com.team8.teamproject.post.repository;

import com.team8.teamproject.post.domain.post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface postRepository extends JpaRepository<post, Long> {
}
