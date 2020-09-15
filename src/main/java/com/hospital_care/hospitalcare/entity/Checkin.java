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
@TableName("t_checkin")
@ApiModel(value="Checkin对象", description="")
public class Checkin implements Serializable {

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

    @ApiModelProperty(value = "客户id")
    private Integer cusId;

    @ApiModelProperty(value = "入住床位id")
    private String bedId;

    @ApiModelProperty(value = "入住日期")
    private String checkinDate;

    @ApiModelProperty(value = "排序")
    private Integer sort;


}
