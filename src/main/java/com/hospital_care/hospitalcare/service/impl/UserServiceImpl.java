package com.hospital_care.hospitalcare.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.mapper.UserMapper;
import com.hospital_care.hospitalcare.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User getUserByUserName(String username) {
        return this.baseMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername,username));
    }
}
