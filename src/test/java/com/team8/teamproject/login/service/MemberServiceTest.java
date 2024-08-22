package com.team8.teamproject.login.service;

import com.team8.teamproject.login.entity.Member;
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
        Member member = new Member(123,"sdfsdf","skepodium@naver.com", "password");

        // When
        Member savedMember = memberService.save(member);

        // Then
        Member foundMember = memberService.findById(savedMember.getuserId()).orElseThrow();
        assertThat(foundMember.getName()).isEqualTo("skyepodium");
        assertThat(foundMember.getEmail()).isEqualTo("skyepodium@example.com");
    }
}
