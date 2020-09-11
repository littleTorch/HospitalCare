package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
public interface UserService extends IService<User> {

    User getUserByUserName(String username);
}
