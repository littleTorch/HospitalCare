package com.hospital_care.hospitalcare.mapper;

import com.hospital_care.hospitalcare.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 根据用户id查询角色id
     * @param userId
     * @return
     */
    UserRole getRoleIdByUserId(@Param("userId") long userId);
}
