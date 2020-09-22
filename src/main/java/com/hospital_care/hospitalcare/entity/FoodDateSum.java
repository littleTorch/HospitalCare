package com.hospital_care.hospitalcare.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDateSum {

    private int date;

    private int day;

    private List<Food> foodList;


}
