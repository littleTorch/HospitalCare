package com.hospital_care.hospitalcare.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import java.util.Objects;

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
@TableName("t_bed")
@ApiModel(value="Bed对象", description="")
public class Bed implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "床位id")
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

    @ApiModelProperty(value = "所属楼房")
    private String floor;

    @ApiModelProperty(value = "房间号码")
    private String roomNo;

    @ApiModelProperty(value = "床位号")
    private String bedNo;

    @ApiModelProperty(value = "类型（0普通（默认）、1特殊）")
    private Integer positionType;

    @ApiModelProperty(value = "床位状态（0空（默认），1使用中）")
    private Integer bedStatus;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private Integer cusId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bed)) return false;
        Bed bed = (Bed) o;
        return Objects.equals(floor, bed.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(floor);
    }
}
