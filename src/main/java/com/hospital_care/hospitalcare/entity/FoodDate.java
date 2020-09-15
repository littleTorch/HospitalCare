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
@TableName("t_food_date")
@ApiModel(value="FoodDate对象", description="")
public class FoodDate implements Serializable {

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

    @ApiModelProperty(value = "星期1234567")
    private Integer date;

    @ApiModelProperty(value = "早中晚012")
    private Integer day;

    @ApiModelProperty(value = "食品名")
    private String foodName;

    @ApiModelProperty(value = "食品标签")
    private String foodTag;

    @ApiModelProperty(value = "食品价格")
    private Integer foodPrice;

    @ApiModelProperty(value = "食品图片")
    private String foodPic;

    @ApiModelProperty(value = "是否清真0否1是")
    private Integer isSteam;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @TableLogic
    private Integer isDeleted;


}
