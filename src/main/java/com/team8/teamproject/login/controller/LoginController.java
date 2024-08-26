package com.team8.teamproject.login.controller;

import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.controller.dto.MemberLoginDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.util.Optional;

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
        // 회원 정보를 저장합니다.
        memberRepository.save(member);
        return "redirect:/"; // 회원가입 후 로그인 페이지로 리디렉션
    }

    @PostMapping("/boards")
    public String loginUser(@RequestParam("email") String email,
                            @RequestParam("password") String password,
                            HttpSession session, Model model) {

        //Optional 처리를 여기서 하면 안된다했는데 언제고치지
        Optional<Member> memberOptional = memberRepository.findByEmail(email);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();

            // 비밀번호가 일치하는지 확인합니다.
            if (member.getPassword().equals(password)) {

                session.setAttribute("loggedInUser", member);
                session.setAttribute("userName", new MemberDto(member));
                return "redirect:/boards"; // 대시보드 페이지로 리디렉션
            } else {
                // 비밀번호가 일치하지 않을 경우 오류 메시지를 모델에 추가합니다.
                model.addAttribute("error", "아이디 또는 비밀번호를 다시 입력하세요. ");
                return "login/login"; // 다시 로그인 페이지로
            }
        } else {
            // 이메일이 일치하는 사용자를 찾지 못한 경우 오류 메시지를 모델에 추가합니다.
            model.addAttribute("error", "아이디를 다시 입력하세요.");
            return "login/login"; // 다시 로그인 페이지로
        }
    }

    @PostMapping("/logout")
    public String logoutUser(HttpSession session) {
        // 로그아웃 시 세션을 무효화합니다.
        session.invalidate();
        return "redirect:/"; // 로그인 페이지로 리디렉션
    }
}
