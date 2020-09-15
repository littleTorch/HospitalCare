package com.hospital_care.hospitalcare.security.detailservice;

import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.UserRole;
import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.hospital_care.hospitalcare.service.PermissionService;
import com.hospital_care.hospitalcare.service.RoleService;
import com.hospital_care.hospitalcare.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    @Autowired
    private PermissionService permissionService;
    @Autowired
    private RoleService roleService;

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
        //3.查询用户所有的权限，设置到 User 的 authorities 中
        UserRole userRole = roleService.getRoleIdByUserId(user.getId());
        System.out.println("UserRole>>>"+userRole);
        List<Permission> codeList = permissionService.getPermissionListByRoleId(userRole.getRoleId());
        System.out.println(">>>>>>>>>>codeList"+codeList.toString());
        //4.把code交给Spring Security
        //4.1获取code
        List<String> collect = codeList.stream().filter(item -> item != null).map(item -> item.getCode()).collect(Collectors.toList());
        //4.2转成数组
        String[] strings = collect.toArray(new String[collect.size()]);
        //4.3把codes转成List<GrantedAuthority>
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(strings);
        authorityList.forEach(System.out::println);
        //4.4设置权限
        user.setAuthorities(authorityList);
        //5.把菜单封装到user
        user.setPermissionList(codeList);
        return user;
    }
}
