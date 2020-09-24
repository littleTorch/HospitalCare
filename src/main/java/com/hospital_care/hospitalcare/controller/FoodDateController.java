package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.mapper.FoodDateMapper;
import com.hospital_care.hospitalcare.mapper.FoodManageMapper;
import com.hospital_care.hospitalcare.mapper.FoodMapper;
import com.hospital_care.hospitalcare.result.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
@RequestMapping("/foodDate")
public class FoodDateController {

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private FoodManageMapper foodManageMapper;

    @Resource
    private FoodDateMapper foodDateMapper;

    @Resource
    private FoodMapper foodMapper;

    @ApiOperation("查询一个用户的膳食日历")
    @PostMapping("/foodDateList")
    public ResultVo findOneFoodData(String recordId){

        QueryWrapper<Customer> wrapper1 = new QueryWrapper<>();
        wrapper1.like("record_id", recordId);
        Customer customer = customerMapper.selectOne(wrapper1);
        //System.out.println(customer);

        QueryWrapper<FoodManage> wrapper2 = new QueryWrapper<>();
        wrapper1.like("cus_id", customer.getId());
        List<FoodManage> foodManages = foodManageMapper.selectList(wrapper2);

        List<FoodDate> foodDateList = new ArrayList<>();

        for (FoodManage foodManage : foodManages) {
            FoodDate foodDate = foodDateMapper.selectById(foodManage.getFoodDateId());
            //System.out.println(foodDate);
            foodDateList.add(foodDate);
        }
        //System.out.println(foodDateList);

        List<FoodDateSum> foodDateSumList = new ArrayList<>();

        FoodDateSum foodDateSum = new FoodDateSum();
        FoodDateSum foodDateSum2 = new FoodDateSum();
        FoodDateSum foodDateSum3 = new FoodDateSum();

        FoodDateSum foodDateSum4 = new FoodDateSum();
        FoodDateSum foodDateSum5 = new FoodDateSum();
        FoodDateSum foodDateSum6 = new FoodDateSum();

        FoodDateSum foodDateSum7 = new FoodDateSum();
        FoodDateSum foodDateSum8 = new FoodDateSum();
        FoodDateSum foodDateSum9 = new FoodDateSum();

        FoodDateSum foodDateSum10 = new FoodDateSum();
        FoodDateSum foodDateSum11 = new FoodDateSum();
        FoodDateSum foodDateSum12 = new FoodDateSum();

        FoodDateSum foodDateSum13 = new FoodDateSum();
        FoodDateSum foodDateSum14 = new FoodDateSum();
        FoodDateSum foodDateSum15 = new FoodDateSum();

        FoodDateSum foodDateSum16 = new FoodDateSum();
        FoodDateSum foodDateSum17 = new FoodDateSum();
        FoodDateSum foodDateSum18 = new FoodDateSum();

        FoodDateSum foodDateSum19 = new FoodDateSum();
        FoodDateSum foodDateSum20 = new FoodDateSum();
        FoodDateSum foodDateSum21 = new FoodDateSum();

        List<Food> foods = new ArrayList<>();
        List<Food> foods2 = new ArrayList<>();
        List<Food> foods3 = new ArrayList<>();

        List<Food> foods4 = new ArrayList<>();
        List<Food> foods5 = new ArrayList<>();
        List<Food> foods6 = new ArrayList<>();

        List<Food> foods7 = new ArrayList<>();
        List<Food> foods8 = new ArrayList<>();
        List<Food> foods9 = new ArrayList<>();

        List<Food> foods10 = new ArrayList<>();
        List<Food> foods11 = new ArrayList<>();
        List<Food> foods12 = new ArrayList<>();

        List<Food> foods13 = new ArrayList<>();
        List<Food> foods14 = new ArrayList<>();
        List<Food> foods15 = new ArrayList<>();

        List<Food> foods16 = new ArrayList<>();
        List<Food> foods17 = new ArrayList<>();
        List<Food> foods18 = new ArrayList<>();

        List<Food> foods19 = new ArrayList<>();
        List<Food> foods20 = new ArrayList<>();
        List<Food> foods21 = new ArrayList<>();

        for (FoodDate foodDate : foodDateList) {

            if (foodDate.getDate() == 0){
                foodDateSum.setDate(foodDate.getDate());
                foodDateSum2.setDate(foodDate.getDate());
                foodDateSum3.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum2.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods2.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum3.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods3.add(food);

                }
                foodDateSum.setFoodList(foods);
                foodDateSum2.setFoodList(foods2);
                foodDateSum3.setFoodList(foods3);
            }

            if (foodDate.getDate() == 1){
                foodDateSum4.setDate(foodDate.getDate());
                foodDateSum5.setDate(foodDate.getDate());
                foodDateSum6.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum4.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods4.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum5.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods5.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum6.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods6.add(food);

                }
                foodDateSum4.setFoodList(foods4);
                foodDateSum5.setFoodList(foods5);
                foodDateSum6.setFoodList(foods6);
            }

            if (foodDate.getDate() == 2){
                foodDateSum7.setDate(foodDate.getDate());
                foodDateSum8.setDate(foodDate.getDate());
                foodDateSum9.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum7.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods7.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum8.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods8.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum9.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods9.add(food);

                }
                foodDateSum7.setFoodList(foods7);
                foodDateSum8.setFoodList(foods8);
                foodDateSum9.setFoodList(foods9);
            }

            if (foodDate.getDate() == 3){
                foodDateSum10.setDate(foodDate.getDate());
                foodDateSum11.setDate(foodDate.getDate());
                foodDateSum12.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum10.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods10.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum11.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods11.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum12.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods12.add(food);

                }
                foodDateSum10.setFoodList(foods10);
                foodDateSum11.setFoodList(foods11);
                foodDateSum12.setFoodList(foods12);
            }

            if (foodDate.getDate() == 4){
                foodDateSum13.setDate(foodDate.getDate());
                foodDateSum14.setDate(foodDate.getDate());
                foodDateSum15.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum13.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods13.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum14.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods14.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum15.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods15.add(food);

                }
                foodDateSum13.setFoodList(foods);
                foodDateSum14.setFoodList(foods2);
                foodDateSum15.setFoodList(foods3);
            }

            if (foodDate.getDate() == 5){
                foodDateSum16.setDate(foodDate.getDate());
                foodDateSum17.setDate(foodDate.getDate());
                foodDateSum18.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum16.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods16.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum17.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods17.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum18.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods18.add(food);

                }
                foodDateSum16.setFoodList(foods);
                foodDateSum17.setFoodList(foods2);
                foodDateSum18.setFoodList(foods3);
            }

            if (foodDate.getDate() == 6){
                foodDateSum19.setDate(foodDate.getDate());
                foodDateSum20.setDate(foodDate.getDate());
                foodDateSum21.setDate(foodDate.getDate());
                if(foodDate.getDay() == 0){
                    foodDateSum19.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods19.add(food);

                }if(foodDate.getDay() == 1){
                    foodDateSum20.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods20.add(food);

                }if(foodDate.getDay() == 2){
                    foodDateSum21.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods21.add(food);

                }
                foodDateSum19.setFoodList(foods);
                foodDateSum20.setFoodList(foods2);
                foodDateSum21.setFoodList(foods3);
            }


        }


//        foodDateSumList.add(foodDateSum);


        foodDateSumList.add(foodDateSum);
        foodDateSumList.add(foodDateSum2);
        foodDateSumList.add(foodDateSum3);
        foodDateSumList.add(foodDateSum4);
        foodDateSumList.add(foodDateSum5);
        foodDateSumList.add(foodDateSum6);
        foodDateSumList.add(foodDateSum7);
        foodDateSumList.add(foodDateSum8);
        foodDateSumList.add(foodDateSum9);
        foodDateSumList.add(foodDateSum10);
        foodDateSumList.add(foodDateSum11);
        foodDateSumList.add(foodDateSum12);
        foodDateSumList.add(foodDateSum13);
        foodDateSumList.add(foodDateSum14);
        foodDateSumList.add(foodDateSum15);
        foodDateSumList.add(foodDateSum16);
        foodDateSumList.add(foodDateSum17);
        foodDateSumList.add(foodDateSum18);
        foodDateSumList.add(foodDateSum19);
        foodDateSumList.add(foodDateSum20);
        foodDateSumList.add(foodDateSum21);

        //System.out.println(foodDateSumList);

        return new ResultVo("查询成功",200,foodDateSumList);
    }

    @ApiOperation("获取食物图片")
    @GetMapping("/pic")
    public void ShowImg(String pic,HttpServletResponse response) throws IOException {

        //这里是存放图片的文件夹地址
        String path= System.getProperty("user.dir")+"\\food";
        response.setContentType("image/jpeg");
        File file=null;
        try {
            file = new File(path+"\\"+pic);
            BufferedImage image= ImageIO.read(file);
            //得到向客户端输出二进制数据的对象
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "jpg", out);
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            System.out.println("系统找不到图像文件："+path+"\\"+pic);
            return;
        }
    }


}

