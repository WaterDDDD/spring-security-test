package com.kai.springsecuritytest.controller;

import com.kai.springsecuritytest.dao.MemberDao;
import com.kai.springsecuritytest.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

        return memberDao.getMemberById(memberId);

    }
}
