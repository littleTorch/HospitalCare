package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 根据用户Id查询所有的权限
     * @param userId
     * @return
     */
    List<Permission> getPermissionByUserId(Integer userId);
    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    List<Permission> getPermissionListByRoleId(Integer roleId);

}
