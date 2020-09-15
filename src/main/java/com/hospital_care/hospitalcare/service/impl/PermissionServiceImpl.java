package com.hospital_care.hospitalcare.service.impl;

import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.hospital_care.hospitalcare.mapper.PermissionMapper;
import com.hospital_care.hospitalcare.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    // @Cacheable(value = "permissions",key = "#userId")
    public List<Permission> getPermissionByUserId(Integer userId) {
        return this.baseMapper.selectPermissionByUserId(userId);
    }
    @Override
    // @Cacheable(value = "permissions",key = "#roleId")
    public List<Permission> getPermissionListByRoleId(Integer roleId) {
        return this.baseMapper.findByRoleId(roleId);
    }
}
