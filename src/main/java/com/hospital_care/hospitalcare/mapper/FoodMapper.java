package com.hospital_care.hospitalcare.mapper;

import com.hospital_care.hospitalcare.entity.Food;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
public interface FoodMapper extends BaseMapper<Food> {

    List<Food> findFoodList();

}
