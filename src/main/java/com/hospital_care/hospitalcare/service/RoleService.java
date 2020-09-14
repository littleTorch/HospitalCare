package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.UserRole;
import com.hospital_care.hospitalcare.entity.role.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 * 角色管理service
 */
public interface RoleService extends IService<Role> {

    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    UserRole getRoleIdByUserId(@Param("userId") Integer userId);

    /**
     * 分配权限
     *
     * @param userRole
     * @return
     */
    void assignRole(UserRole userRole);

}
