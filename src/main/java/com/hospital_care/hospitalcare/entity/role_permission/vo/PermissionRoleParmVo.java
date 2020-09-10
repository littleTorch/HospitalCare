package com.hospital_care.hospitalcare.entity.role_permission.vo;

import com.hospital_care.hospitalcare.entity.permission.vo.TreeVo;
import lombok.Data;

import java.util.List;

@Data
public class PermissionRoleParmVo {
    private List<TreeVo> list;
    private Long roleId;
}
