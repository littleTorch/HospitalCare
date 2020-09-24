package com.hospital_care.hospitalcare.entity.checkout;

import lombok.Data;

@Data
public class CheckoutEditVo {

    private Integer id;

    private String createTime;

    private String createBy;

    private String updateTime;

    private String updateBy;

    private Integer isDeleted;

    private Integer cusId;

    private String cusName;

    private String checkoutDate;

    private Integer checkoutType;

    private String checkoutReason;

    private Integer status;

    private String auditPerson;

    private Integer audit;

    private String auditTime;

    private Integer bedId;

    private String floor;

    private String roomNo;

    private String bedNo;

}
