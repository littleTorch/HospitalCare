package com.hospital_care.hospitalcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;

import java.util.Collection;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.List;

import com.hospital_care.hospitalcare.entity.permission.Permission;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="User对象", description="")
public class User implements Serializable, UserDetails {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "最新一次修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    @ApiModelProperty(value = "修改者")
    private String updateBy;

    @ApiModelProperty(value = "0未删除（默认），1已删除")
    @TableLogic
    private Integer isDeleted;

    private String username;

    private String password;

    @ApiModelProperty(value = "0男，1女")
    private Integer sex;

    private String email;

    private String phone;

    private String userCode;

    //用户权限列表,不属于用户表字段，需要排除
    @ApiModelProperty(value = "用户权限列表")
    @TableField(exist = false)
    List<Permission> permissionList;

    //由于authorities不是数据库里面的自动，所以要排除他，不然mybatis-plus找不到该字段会报错
    @ApiModelProperty(value = "权限")
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    //账户没有过期
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //账户没有锁定
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //密码没有过期
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //账户是否可用
    @Override
    public boolean isEnabled() {
        return true;
    }
}
