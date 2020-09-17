package com.hospital_care.hospitalcare.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="MyMsg对象", description="")
public class MyMsg {

    private static final long serialVersionUID=1L;

    private User user;
    private Emp emp;

}
