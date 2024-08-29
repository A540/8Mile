package com.team8.teamproject.login.service;


import com.team8.teamproject.login.controller.dto.MemberDto;
import com.team8.teamproject.login.entity.Member;
import com.team8.teamproject.login.repository.MemberRepository;
import com.team8.teamproject.oauth.dto.PrincipalDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailService  implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override //UserDetailsService를 받아 데이터베이스에 email이 존재하는지 확인하는 메소드.
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("해당 이용자는 없다. : " + email));
        MemberDto memberDto = new MemberDto(member);
//        return org.springframework.security.core.userdetails.User
//                .builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .roles(user.getRole().name())
//                .build();
        return new PrincipalDetails(memberDto,null );
    }
}