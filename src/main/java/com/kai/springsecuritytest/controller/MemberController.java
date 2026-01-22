package com.kai.springsecuritytest.controller;

import com.kai.springsecuritytest.dao.MemberDao;
import com.kai.springsecuritytest.model.Member;
import com.kai.springsecuritytest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class MemberController {

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public Member register(@RequestBody Member member) {

        String password = passwordEncoder.encode(member.getPassword());
        member.setPassword(password);

        Integer memberId = memberDao.createMember(member);

        Role normalRole = memberDao.getRoleByRoleName("ROLE_NORMAL_MEMBER");
        memberDao.addRoleByMemberId(memberId, normalRole);

        return memberDao.getMemberById(memberId);

    }

    @PostMapping("/login")
    public String login(Authentication authentication) {

        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        return "Hello " + username + "權限為" + authorities;

    }
}
