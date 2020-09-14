package com.hospital_care.hospitalcare.mapper;

import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户Id查询所有的权限
     * @param userId
     * @return
     */
    List<Permission> selectPermissionByUserId(@Param("userId") Long userId);
    /**
     * 根据角色id查询所有的权限
     * @param roleId
     * @return
     */
    List<Permission> findByRoleId(@Param("roleId") Integer roleId);

}
