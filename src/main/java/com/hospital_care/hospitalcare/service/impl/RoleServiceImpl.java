package com.hospital_care.hospitalcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.UserRole;
import com.hospital_care.hospitalcare.entity.role.Role;
import com.hospital_care.hospitalcare.mapper.RoleMapper;
import com.hospital_care.hospitalcare.mapper.UserRoleMapper;
import com.hospital_care.hospitalcare.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public UserRole getRoleIdByUserId(Integer userId) {
        return userRoleMapper.getRoleIdByUserId(userId);
        //return userRoleMapper.selectOne(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, userId));
    }

    @Override
    public void assignRole(UserRole userRole) {
        LambdaQueryWrapper<UserRole> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(UserRole::getUserId, userRole.getUserId()).eq(UserRole::getRoleId, userRole.getRoleId());
        userRoleMapper.delete(lambdaQuery);
        userRoleMapper.insert(userRole);
    }
}
