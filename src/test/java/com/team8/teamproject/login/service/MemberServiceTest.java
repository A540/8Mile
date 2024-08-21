package com.team8.teamproject.login.service;

import com.team8.teamproject.login.domain.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;


    @Test
    void tests() {
        // Given
        Member member = new Member();
        member.setName("skyepodium");
        member.setEmail("skyepodium@example.com");
        member.setPassword("password123");

        // When
        Member savedMember = memberService.save(member);

        // Then
        Member foundMember = memberService.findById(savedMember.getId()).orElseThrow();
        assertThat(foundMember.getName()).isEqualTo("skyepodium");
        assertThat(foundMember.getEmail()).isEqualTo("skyepodium@example.com");
    }
}
