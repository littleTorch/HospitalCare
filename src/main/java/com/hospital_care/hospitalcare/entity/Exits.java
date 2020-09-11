package com.hospital_care.hospitalcare.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
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
 * @since 2020-09-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Exits对象", description="")
public class Exits implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @ApiModelProperty(value = "创建者")
    private String createBy;

    @ApiModelProperty(value = "更新时间")
    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @ApiModelProperty(value = "客户id")
    private Integer cusId;

    @ApiModelProperty(value = "退住时间")
    private String exitDate;

    @ApiModelProperty(value = "退住类型（0回家、1逝世、2换院）")
    private Integer exitType;

    @ApiModelProperty(value = "退住原因")
    private String exitReason;

    @ApiModelProperty(value = "0处理中、1已退住")
    private Integer status;

    @ApiModelProperty(value = "审核人")
    private String auditPerson;

    @ApiModelProperty(value = "审核情况（0未审核、1审核通过、2审核不通过）")
    private Integer audit;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
