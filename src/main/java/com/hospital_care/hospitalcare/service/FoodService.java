package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.Food;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
public interface FoodService extends IService<Food> {

    List<Food> findFoodList();

}
