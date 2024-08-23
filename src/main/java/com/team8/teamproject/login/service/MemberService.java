package com.team8.teamproject.login.service;

import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.exception.MemberNotFoundException;
import com.team8.teamproject.login.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private EntityManager em;

    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member save(Member member) {
        return memberRepository.save(member);
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new MemberNotFoundException("Member with id " + id + " not found"));
    }


    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new MemberNotFoundException("Member with email " + email + " not found"));
    }


    public List<Member> findAll() {
        return memberRepository.findAll();
    }
}

