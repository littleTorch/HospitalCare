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
@TableName("t_food")
@ApiModel(value="Food对象", description="")
public class Food implements Serializable {

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

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "食物名称")
    private String foodName;

    @ApiModelProperty(value = "食物类型（0-1大小荤、2素菜、3甜点、4水果、5套餐）")
    private Integer foodType;

    @ApiModelProperty(value = "食物价格")
    private Integer foodPrice;

    @ApiModelProperty(value = "食物图片")
    private String foodPic;

    @ApiModelProperty(value = "是否清真0否1是")
    private Integer isSteam;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
