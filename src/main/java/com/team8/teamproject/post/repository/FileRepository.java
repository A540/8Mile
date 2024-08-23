package com.team8.teamproject.post.repository;

import com.team8.teamproject.post.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<Files, Long> {
}
