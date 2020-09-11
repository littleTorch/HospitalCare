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
@ApiModel(value="Out对象", description="")
public class Out implements Serializable {

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

    @ApiModelProperty(value = "外出事由")
    private String outReason;

    @ApiModelProperty(value = "外出时间")
    private String outTime;

    @ApiModelProperty(value = "预计回院时间")
    private String expectTime;

    @ApiModelProperty(value = "实际回院时间")
    private String actualTime;

    @ApiModelProperty(value = "陪同人")
    private String accompany;

    @ApiModelProperty(value = "陪同人联系电话")
    private String accompanyPhone;

    @ApiModelProperty(value = "审批人")
    private String auditPerson;

    @ApiModelProperty(value = "审核状态")
    private Integer audit;

    @ApiModelProperty(value = "审核时间")
    private String auditTime;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
