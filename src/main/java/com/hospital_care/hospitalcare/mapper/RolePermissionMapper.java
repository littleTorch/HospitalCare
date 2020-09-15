package com.hospital_care.hospitalcare.mapper;

import com.hospital_care.hospitalcare.entity.role_permission.RolePermission;
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
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    //批量新增权限
    boolean saveRolePermissions(@Param("roleId") Integer roleId, @Param("perIds") List<Integer> perIds);
}
