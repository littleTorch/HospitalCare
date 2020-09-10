package com.hospital_care.hospitalcare.security.detailservice;

import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 认证处理类
 * 查询数据库是否有用户
 */
@Slf4j
@Component("customerUserDetailsService")
public class CustomerUserDetailsService implements UserDetailsService {

    //注入UserService
    @Autowired
    private UserService userService;

    //这里需要注入PasswordEncoder，不然会报错的
    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户username查询数据库是否有用户
        User user = userService.getUserByUserName(username);
        System.out.println(">>>>>"+user);
        //2.用户不存在抛出异常
        if(null == user){
            throw new UsernameNotFoundException("用户名或密码错误!");
        }
        return user;
    }
}
