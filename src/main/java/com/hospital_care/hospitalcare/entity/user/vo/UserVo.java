package com.hospital_care.hospitalcare.entity.user.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.hospital_care.hospitalcare.entity.permission.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Data
public class UserVo{

    private Integer id;
    //private String createBy;
    private String updateBy;
    private String username;
    //private String password;
    private Integer sex;
    private String email;
    private String phone;
    private Integer empId;
    //private String userIcon;
    //private String empName;

}

