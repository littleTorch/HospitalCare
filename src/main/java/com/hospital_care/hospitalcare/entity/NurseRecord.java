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
@ApiModel(value="NurseRecord对象", description="")
public class NurseRecord implements Serializable {

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

    @ApiModelProperty(value = "护理项目id")
    private Integer projectId;

    @ApiModelProperty(value = "开始护理时间")
    private String projectTime;

    @ApiModelProperty(value = "护理次数")
    private Integer num;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "护理人员id")
    private Integer empId;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
