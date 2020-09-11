package com.hospital_care.hospitalcare.entity.permission;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@ApiModel(value="Permission对象", description="")
public class Permission implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父权限id")
    private Integer parentId;

    @ApiModelProperty(value = "父权限名")
    private String parentName;

    @ApiModelProperty(value = "权限名称")
    private String label;

    @ApiModelProperty(value = "授权标识符")
    private String code;

    @ApiModelProperty(value = "路由地址")
    private String path;

    @ApiModelProperty(value = "路径名")
    private String name;

    @ApiModelProperty(value = "授权路径")
    private String url;

    @ApiModelProperty(value = "序号")
    private Integer orderNum;

    @ApiModelProperty(value = "类型（0目录，1菜单，2按钮）")
    private String type;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    private Integer isHome;

    //不是数据库的字段需要排除
    @TableField(exist = false)
    private List<Permission> children = new ArrayList<>();
}
