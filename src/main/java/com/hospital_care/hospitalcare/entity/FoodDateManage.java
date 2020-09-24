package com.hospital_care.hospitalcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FoodDateManage {

    private Integer id;

    private String cusName;

    private Integer date;

    private Integer day;

    private String foodName;

    private Integer foodType;

    private String foodTag;

    private Long foodPrice;

    private String foodPic;

    private Integer isSteam;


}
