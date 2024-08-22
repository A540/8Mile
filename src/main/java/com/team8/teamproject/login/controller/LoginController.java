package com.team8.teamproject.login.controller;

import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    private final MemberRepository memberRepository;

    public LoginController(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @GetMapping("/")
    public String showLoginPage() {
        return "login/login"; // src/main/resources/templates/login/login.html
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "login/signup"; // src/main/resources/templates/login/signup.html
    }

    @PostMapping("/signup")
    public String createUser(@ModelAttribute Member member) {

        memberRepository.save(member);
        return "redirect:/";
    }
}
