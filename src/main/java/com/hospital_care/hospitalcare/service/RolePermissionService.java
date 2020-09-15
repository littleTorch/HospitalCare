package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.role_permission.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 分配权限保存
     * @param
     */
    void saveAssignRole(Integer roleId, List<Integer> collect);

}
