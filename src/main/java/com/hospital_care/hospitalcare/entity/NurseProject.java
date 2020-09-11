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
@ApiModel(value="NurseProject对象", description="")
public class NurseProject implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "编号")
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

    @ApiModelProperty(value = "护理等级")
    private Integer level;

    @ApiModelProperty(value = "护理项目名称")
    private String projectName;

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "描述")
    private String details;

    @ApiModelProperty(value = "是否增值服务0否1是")
    private Integer addService;

    @ApiModelProperty(value = "状态1启用2停用")
    private Integer status;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
