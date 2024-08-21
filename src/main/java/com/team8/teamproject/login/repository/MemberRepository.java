package com.team8.teamproject.login.repository;

import com.team8.teamproject.login.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);

    // 모든 멤버를 리스트로 반환하는 메소드
    List<Member> findAll();
}
