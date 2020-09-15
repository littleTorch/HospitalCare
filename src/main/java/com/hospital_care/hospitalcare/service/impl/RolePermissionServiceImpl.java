package com.hospital_care.hospitalcare.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.role_permission.RolePermission;
import com.hospital_care.hospitalcare.mapper.RolePermissionMapper;
import com.hospital_care.hospitalcare.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    @Transactional
    public void saveAssignRole(Integer roleId, List<Integer> collect) {
        //1.删除原来角色的权限
        QueryWrapper<RolePermission> query = new QueryWrapper<>();
        query.lambda().eq(RolePermission::getRoleId,roleId);
        this.baseMapper.delete(query);
        //2.插入新权限
        this.baseMapper.saveRolePermissions(roleId,collect);
    }
}
