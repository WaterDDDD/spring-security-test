package com.kai.springsecuritytest.controller;


import com.kai.springsecuritytest.dao.MemberDao;
import com.kai.springsecuritytest.dto.SubscribeRequest;
import com.kai.springsecuritytest.dto.UnsubscribeRequest;
import com.kai.springsecuritytest.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SubscriptionController {

    @Autowired
    private MemberDao memberDao;

    @PostMapping("/subscribe")
    public String subscribe(@RequestBody SubscribeRequest subscribeRequest) {

        Integer id = subscribeRequest.getMemberId();

        List<Role> roleList = memberDao.getRolesByMemberId(id);

        boolean isSubscribed =  checkSubscribeStatus(roleList);

        if (isSubscribed) {
            return "以訂閱過";
        } else {
            Role vipRole = memberDao.getRoleByRoleName("VIP_MEMBER");
            memberDao.addRoleByMemberId(id, vipRole);

            return "訂閱成功";
        }
    }

    @PostMapping("/unsubscribe")
    public String unsubscribe(@RequestBody UnsubscribeRequest unsubscribeRequest) {

        Integer id = unsubscribeRequest.getMemberId();
        List<Role> roleList = memberDao.getRolesByMemberId(id);

        boolean isSubscribed =  checkSubscribeStatus(roleList);

        if (isSubscribed) {
            Role vipRole = memberDao.getRoleByRoleName("VIP_MEMBER");
            memberDao.removeRoleFromMemberId(id, vipRole);

            return "取消訂閱成功";

        } else {
            return "尚未訂閱";
        }

    }

    protected Boolean checkSubscribeStatus(List<Role> roleList) {

        boolean isSubscribe = false;

        for(Role role : roleList) {
            if (role.getRoleName().equals("VIP_MEMBER")) {
                isSubscribe = true;
            }
        }

        return isSubscribe;
    }
}
