package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.mapper.FoodDateMapper;
import com.hospital_care.hospitalcare.mapper.FoodManageMapper;
import com.hospital_care.hospitalcare.mapper.FoodMapper;
import com.hospital_care.hospitalcare.result.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/foodManage")
public class FoodManageController {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private FoodManageMapper foodManageMapper;

    @Resource
    private FoodDateMapper foodDateMapper;

    @Resource
    private FoodMapper foodMapper;


    @ApiOperation("查询一个用户的膳食日历")
    @PostMapping("/foodManageList")
    public ResultVo findOneFoodData(String cusName){
        //System.out.println(recordId);
        QueryWrapper<Customer> wrapper1 = new QueryWrapper<>();
        wrapper1.like("cus_name", cusName);
        Customer customer = customerMapper.selectOne(wrapper1);
        //System.out.println(customer);

        QueryWrapper<FoodManage> wrapper2 = new QueryWrapper<>();
        wrapper1.like("cus_name", customer.getCusName());
        List<FoodManage> foodManages = foodManageMapper.selectList(wrapper2);

        List<FoodDate> foodDateList = new ArrayList<>();

        for (FoodManage foodManage : foodManages) {
            FoodDate foodDate = foodDateMapper.selectById(foodManage.getFoodDateId());
            foodDateList.add(foodDate);
        }

        for (FoodDate foodDate : foodDateList) {
           // System.out.println(foodDate);
            Food food = foodMapper.selFoodById(foodDate.getFoodId());
            //System.out.println(foods);
            foodDate.setFood(food);
        }

        //System.out.println(foodDateList);

        return new ResultVo("查询成功",200,foodDateList);
    }

    @ApiOperation("新增一个用户的膳食日历")
    @PostMapping("/addOneFoodDate")
    public ResultVo addOneFoodDate(FoodDateManage foodDateManage){
        //System.out.println(foodDateManage);
        QueryWrapper<Customer> wrapper1 = new QueryWrapper<>();
        wrapper1.like("cus_name", foodDateManage.getCusName());
        Customer customer = customerMapper.selectOne(wrapper1);

        FoodDate foodDate = new FoodDate();
        foodDate.setDate(foodDateManage.getDate());
        foodDate.setDay(foodDateManage.getDay());


        Food food = new Food();
        food.setFoodName(foodDateManage.getFoodName());
        food.setFoodType(foodDateManage.getFoodType());
        food.setFoodTag(foodDateManage.getFoodTag());
        food.setFoodPrice(foodDateManage.getFoodPrice());
        food.setFoodPic(foodDateManage.getFoodPic());
        food.setIsSteam(foodDateManage.getIsSteam());
       // System.out.println(food);
        int i = foodMapper.insert(food);
        //System.out.println(i);
        if (i>0){
            Food selLikeFood = foodMapper.selLikeFood(foodDateManage.getFoodName(),foodDateManage.getFoodTag());
            foodDate.setFoodId(selLikeFood.getId());
            //System.out.println(foodDate);
            int insert = foodDateMapper.insert(foodDate);
            if (insert>0) {
                QueryWrapper<FoodDate> wrapper2 = new QueryWrapper<>();
                //System.out.println(selLikeFood.getId() + " " + foodDateManage.getDate() + " " + foodDateManage.getDay());
                wrapper2.like("food_id", selLikeFood.getId()).like("date",
                        foodDateManage.getDate()).like("day", foodDateManage.getDay());
                FoodDate foodDate1 = foodDateMapper.selectOne(wrapper2);
                //System.out.println(foodDate1);
                FoodManage foodManage = new FoodManage();
                foodManage.setCusId(customer.getId());
                foodManage.setFoodDateId(foodDate1.getId());
                int i2 = foodManageMapper.insert(foodManage);
                if (i2>0){
                    return new ResultVo("增加成功",200,null);
                }
            }
//
        }

        //System.out.println(customer);
        return new ResultVo("增加失败",500,null);
    }


    @ApiOperation("修改一个用户的膳食日历")
    @PostMapping("/updateFoodDate")
    public ResultVo updateFoodDate(FoodDateManage foodDateManage){
        System.out.println(foodDateManage);
        FoodDate foodDate = new FoodDate();
        foodDate.setId(foodDateManage.getId());
        foodDate.setDate(foodDateManage.getDate());
        foodDate.setDay(foodDateManage.getDay());

        Food food = new Food();
        food.setFoodName(foodDateManage.getFoodName());
        food.setFoodType(foodDateManage.getFoodType());
        food.setFoodTag(foodDateManage.getFoodTag());
        food.setFoodPrice(foodDateManage.getFoodPrice());
        food.setFoodPic(foodDateManage.getFoodPic());
        food.setIsSteam(foodDateManage.getIsSteam());
        // System.out.println(food);
        int i = foodMapper.insert(food);

        if (i>0){
            Food selLikeFood = foodMapper.selLikeFood(foodDateManage.getFoodName(),foodDateManage.getFoodTag());
            foodDate.setFoodId(selLikeFood.getId());
            //System.out.println(foodDate);
            int update = foodDateMapper.updateById(foodDate);
            if (update>0) {
                return new ResultVo("修改成功", 200, null);
            }

        }

        //System.out.println(customer);
        return new ResultVo("修改失败",500,null);


    }

    @ApiOperation("修改一个用户的膳食日历")
    @PostMapping("/deleteOne")
    public ResultVo deleteOne(String foodDateId){

        QueryWrapper<FoodManage> wrapper = new QueryWrapper<>();
        wrapper.like("food_date_id",foodDateId);

        FoodManage foodManage = foodManageMapper.selectOne(wrapper);
        int i1 = foodManageMapper.deleteById(foodManage.getId());
        System.out.println(i1);
        if (i1>0){
            int i = foodDateMapper.deleteById(foodDateId);
            if (i > 0) {
                System.out.println(i);
                return new ResultVo("删除成功", 200, null);
            }
        }

        return new ResultVo("删除失败", 200, null);
    }


}

