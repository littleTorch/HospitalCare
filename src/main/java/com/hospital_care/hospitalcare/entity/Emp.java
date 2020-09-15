package com.hospital_care.hospitalcare.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
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
 * @since 2020-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("t_emp")
@ApiModel(value="Emp对象", description="")
public class Emp implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    private String updateBy;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "员工类型（0管理人员、1医务人员、2护理人员）")
    private Integer empType;

    @ApiModelProperty(value = "员工名")
    private String empName;

    @ApiModelProperty(value = "性别（默认0男、1女）")
    private Integer sex;

    @ApiModelProperty(value = "入职日期")
    private String entryDate;

    @ApiModelProperty(value = "操作人")
    private String operateName;

    @ApiModelProperty(value = "职务")
    private String duty;

    @ApiModelProperty(value = "职称")
    private String possionalName;

    @ApiModelProperty(value = "简介")
    private String brief;

    @ApiModelProperty(value = "联系号码")
    private String phone;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
