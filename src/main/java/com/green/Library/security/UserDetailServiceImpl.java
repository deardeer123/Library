package com.green.Library.security;

import com.green.Library.web.member.service.MemberServiceImpl;
import com.green.Library.web.member.vo.MemberVO;
import jakarta.annotation.Resource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.naming.Name;
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {
    @Resource(name = "memberService")
    private MemberServiceImpl memberService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO loginInfo = memberService.login(username);

        User user = (User) User.builder()
                .username(loginInfo.getUserId())
                .password("{noop}"+loginInfo.getUserPw())
                .roles(loginInfo.getIsAdmin())
                .build();
        return user;
    }
}
