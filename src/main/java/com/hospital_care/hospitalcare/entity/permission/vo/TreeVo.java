package com.hospital_care.hospitalcare.entity.permission.vo;

import lombok.Data;

@Data
public class TreeVo {
    //树的id
    private Integer id;
    //树的父id
    private Integer pid;
    //树的名称
    private String name;
    //是否展开
    private Boolean open;
    //是否选中
    private Boolean checked;
}
