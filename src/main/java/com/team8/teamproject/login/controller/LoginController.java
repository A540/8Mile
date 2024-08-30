package com.team8.teamproject.login.controller;

import com.team8.teamproject.board.controller.dto.BoardForm;
import com.team8.teamproject.board.controller.dto.MemberViewDto;
import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.Map;
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


    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        // 현재 인증된 사용자를 확인
        System.out.println("123");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 이미 로그인된 사용자가 있다면
        if (authentication != null && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)) {
            return "redirect:/boards";
        }

        // 로그인되지 않은 경우 로그인 페이지 표시
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
        return "redirect:/login"; // 회원가입 후 로그인 페이지로 리디렉션
    }


    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session, Model model, RedirectAttributes redirectAttributes) {

        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 비밀번호가 일치하는지 확인합니다.
            if (passwordEncoder.matches(password, member.getPassword())) {
                // 로그인 성공 시 MemberDto 객체를 생성
                MemberDto memberDto = new MemberDto(member);
                MemberViewDto memberViewDto = new MemberViewDto(memberDto);
                // 세션에 MemberDto 객체를 저장
                session.setAttribute("member", memberViewDto);
                redirectAttributes.addFlashAttribute("member",  memberViewDto);

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


    @GetMapping("/auth/check-authentication")
    @ResponseBody
    public Map<String, Object> checkAuthentication(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession(false); // false means do not create a new session if one does not exist

        boolean isAuthenticated = authentication != null
                && authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken);

        // 세션 ID 확인
        String sessionId = (session != null) ? session.getId() : null;

        // 응답에 인증 상태와 세션 ID 포함
        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", isAuthenticated);
        response.put("sessionId", sessionId);

        return response;
    }


    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request, HttpServletResponse response) {
        // Spring Security 로그아웃 처리
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        // 현재 세션을 가져옵니다.
        HttpSession session = request.getSession(false); // false는 세션이 없으면 null 반환

        if (session != null) {
            // 세션 ID를 가져옵니다.
            String sessionId = session.getId();

            // Redis에서 세션 데이터를 삭제합니다.
            redisTemplate.delete("spring:session:sessions:" + sessionId);

            // 세션 무효화
            session.invalidate();
        }

        // 로그아웃 후 로그인 페이지로 리디렉션
        return "redirect:/login";
    }
}