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
@TableName("t_customer")
@ApiModel(value="Customer对象", description="")
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    @TableField(fill = FieldFill.UPDATE)
    private String updateBy;

    @TableField(fill = FieldFill.UPDATE)
    private String updateTime;

    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "客户名")
    private String cusName;

    @ApiModelProperty(value = "性别（默认0男，1女）")
    private Integer cusSex;

    @ApiModelProperty(value = "年龄")
    private Integer cusAge;

    @ApiModelProperty(value = "身份证号")
    private String cusIdcard;

    @ApiModelProperty(value = "档案号")
    private String recordId;

    @ApiModelProperty(value = "老人类型（0普通老人、1残疾老人）")
    private String elderType;

    @ApiModelProperty(value = "合同到期日期")
    private String expirationDate;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "身心状况")
    private String psychologicalStatus;

    @ApiModelProperty(value = "注意")
    private String attentin;

    @ApiModelProperty(value = "出生年月")
    private String birthday;

    @ApiModelProperty(value = "身高")
    private String height;

    @ApiModelProperty(value = "体重")
    private String weight;

    @ApiModelProperty(value = "婚姻状态（0已婚（默认）、1丧偶/丧夫、2未婚）")
    private Integer maritalStatus;

    @ApiModelProperty(value = "血型")
    private String bloodType;

    @ApiModelProperty(value = "头像存储")
    private String headImg;


}
