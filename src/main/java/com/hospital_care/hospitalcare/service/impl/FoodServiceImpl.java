package com.hospital_care.hospitalcare.service.impl;

import com.hospital_care.hospitalcare.entity.Food;
import com.hospital_care.hospitalcare.mapper.FoodMapper;
import com.hospital_care.hospitalcare.service.FoodService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@Service
public class FoodServiceImpl extends ServiceImpl<FoodMapper, Food> implements FoodService {

    @Override
    public List<Food> findFoodList() {
        return this.baseMapper.findFoodList();
    }
}
