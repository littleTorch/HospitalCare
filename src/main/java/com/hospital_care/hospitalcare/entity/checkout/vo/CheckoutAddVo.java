package com.hospital_care.hospitalcare.entity.checkout.vo;

import lombok.Data;

@Data
public class CheckoutAddVo {

    private Integer cusId;
    private String cusName;
    private boolean disabled = false;
    private Integer bedId;
    private String floor;
    private String roomNo;
    private String bedNo;

    private String label;

}
