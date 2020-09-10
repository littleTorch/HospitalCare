package com.hospital_care.hospitalcare.entity.permission.vo;

import com.hospital_care.hospitalcare.entity.permission.Permission;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单返回实体
 */
@Data
public class MenuVo implements Serializable {
    private List<Permission> menuList;
    private List<String> authList;
    private List<Permission> routerList;
    private String token;
    private Integer roleId;
    private Integer userId;
    private String userNickname;
}

