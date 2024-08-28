package com.team8.teamproject.login.controller;

import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.http.HttpRequest;
import java.util.Optional;

@Controller
public class LoginController {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    private final RedisTemplate<String, Object> redisTemplate;
    public LoginController(MemberRepository memberRepository, PasswordEncoder passwordEncoder, RedisTemplate<String, Object> redisTemplate) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.redisTemplate = redisTemplate;
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
    public String createUser(@ModelAttribute Member member, Model model) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            model.addAttribute("error", "이미 존재하는 이메일입니다.");
            return "login/signup"; // 에러 메시지와 함께 회원가입 페이지로 다시 리턴
        }

        if (memberRepository.existsByUserName(member.getUserName())) {
            model.addAttribute("error", "이미 존재하는 닉네임(활동명)입니다.");
            return "login/signup"; // 에러 메시지와 함께 회원가입 페이지로 다시 리턴
        }

        // 중복이 없으면 회원 정보를 저장
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword); // 암호화된 비밀번호 설정
        memberRepository.save(member);
        return "redirect:/"; // 회원가입 후 로그인 페이지로 리디렉션
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session, Model model) {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 비밀번호가 일치하는지 확인합니다.
            if (passwordEncoder.matches(password, member.getPassword())) {
                // 로그인 성공 시 MemberDto 객체를 생성
                MemberDto memberDto = new MemberDto(member);

                // 세션에 MemberDto 객체를 저장
                session.setAttribute("userDetails", memberDto);

                return "redirect:/boards";
            } else {
                model.addAttribute("error", "아이디 또는 비밀번호를 다시 입력하세요.");
                return "login/login";
            }
        } else {
            model.addAttribute("error", "아이디를 다시 입력하세요.");
            return "login/login";
        }
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        // 세션 ID를 가져옵니다.
        String sessionId = session.getId();

        // Redis에서 세션 데이터를 삭제합니다.
        redisTemplate.delete("spring:session:sessions:" + sessionId);
        redisTemplate.delete(sessionId);

        // 로그아웃 시 세션을 무효화합니다.
        session.invalidate();
        return "redirect:/"; // 로그인 페이지로 리디렉션
    }
}
