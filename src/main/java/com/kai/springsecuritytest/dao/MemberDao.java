package com.kai.springsecuritytest.dao;

import com.kai.springsecuritytest.model.Member;
import com.kai.springsecuritytest.model.Role;

import java.util.List;

public interface MemberDao {

    Member getMemberById(Integer memberId);

    Member gerMemberByEmail(String email);

    Integer createMember(Member member);

    List<Role> getRolesByMemberId(Integer memberId);

    void addRoleByMemberId(Integer memberId, Role role);

    void removeRoleFromMemberId(Integer memberId, Role role);

    Role getRoleByRoleName(String roleName);
}
