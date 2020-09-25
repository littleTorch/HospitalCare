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
    Food selFoodById(int id);

    Food findFoodById(String id);

    List<Food> selFoodList();

    List<Food> selLike(String foodName);

    List<Food> selLikeFood(String foodName,String foodTag);

    int addFood(Food food);

    int delFood(String foodId);

    int updateFood(Food food);
    
}
