package com.kai.springsecuritytest.dao.impl;


import com.kai.springsecuritytest.dao.MemberDao;
import com.kai.springsecuritytest.model.Member;
import com.kai.springsecuritytest.model.Role;
import com.kai.springsecuritytest.rowmapper.MemberRowMapper;
import com.kai.springsecuritytest.rowmapper.RoleRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class MemberDaoImpl implements MemberDao {


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    private MemberRowMapper memberRowMapper;

    @Autowired
    private RoleRowMapper roleRowMapper;

    @Override
    public Member getMemberById(Integer memberId) {

        String sql = "SELECT email, password, name, age FROM member WHERE member_id = :memberId";

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);

        List<Member> memberList = namedParameterJdbcTemplate.query(sql, map, memberRowMapper);

        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Member gerMemberByEmail(String email) {

        String sql = "SELECT member_id, email, password, name, age FROM member WHERE email = :email";

        Map<String, Object> map = new HashMap<>();
        map.put("email", email);

        List<Member> memberList = namedParameterJdbcTemplate.query(sql, map, memberRowMapper);

        if (memberList.size() > 0) {
            return memberList.get(0);
        } else {
            return null;
        }

    }

    @Override
    public Integer createMember(Member member) {

        String sql = "INSERT INTO member(email, password, name, age) VALUES(:email, :password, :name, :age)";

        Map<String, Object> map = new HashMap<>();
        map.put("email", member.getEmail());
        map.put("password", member.getPassword());
        map.put("name", member.getName());
        map.put("age", member.getAge());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int memberId = keyHolder.getKey().intValue();

        return memberId;

    }

    @Override
    public List<Role> getRolesByMemberId(Integer memberId) {
        String sql = """
                        SELECT role.role_id, role.role_name FROM role    
                        JOIN member_has_role ON member_has_role.role_id = role.role_id
                        WHERE member_has_role.member_id = :memberId
                """;

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);

        List<Role> roleList = namedParameterJdbcTemplate.query(sql, map, roleRowMapper);

        return roleList;
    }

    @Override
    public void addRoleByMemberId(Integer memberId, Role role) {

        String sql = "INSERT INTO member_has_role(member_id, role_id) VALUES (:memberId, :roleId)";

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("roleIde", role.getRoleId());

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public void removeRoleFromMemberId(Integer memberId, Role role) {

        String sql = "DELETE FROM member_has_role WHERE member_id = :memberId AND role_id = :roleId";

        Map<String, Object> map = new HashMap<>();
        map.put("memberId", memberId);
        map.put("roleIde", role.getRoleId());

        namedParameterJdbcTemplate.update(sql, map);

    }

    @Override
    public Role getRoleByRoleName(String roleName) {

        String sql = "SELECT role_name FROM role WHERE role_name = :roleName";

        Map<String, Object> map = new HashMap<>();
        map.put("roleName", roleName);

        List<Role> roleList = namedParameterJdbcTemplate.query(sql, map, roleRowMapper);

        if (roleList.isEmpty()) {
            return null;
        } else {
            return roleList.get(0);
        }
    }
}
