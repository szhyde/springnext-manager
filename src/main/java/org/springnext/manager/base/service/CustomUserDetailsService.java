package org.springnext.manager.base.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springnext.manager.base.dto.SecurityUser;
import org.springnext.manager.base.entity.User;

public class CustomUserDetailsService implements UserDetailsService {
    @Autowired  //数据库服务类
    private UserService suserService;

    @Override
    public UserDetails loadUserByUsername(String loginName) throws UsernameNotFoundException {
        //SUser对应数据库中的用户表，是最终存储用户和密码的表，可自定义
        //本例使用SUser中的email作为用户名:
        User user = suserService.findUserByLoginName(loginName); 

        if (user == null) {

            throw new UsernameNotFoundException("UserName " + loginName + " not found");

        }

        // SecurityUser实现UserDetails并将User的Email映射为username
        SecurityUser securityUser = new SecurityUser(user);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return securityUser; 

    }

}
