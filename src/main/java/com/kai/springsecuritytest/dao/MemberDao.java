package com.kai.springsecuritytest.dao;

import com.kai.springsecuritytest.model.Member;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member gerMemberByEmail(String email);

    Integer createMember(Member member);
}
