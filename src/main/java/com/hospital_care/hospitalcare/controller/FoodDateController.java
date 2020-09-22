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

        List<Food> foods = new ArrayList<>();
        List<Food> foods2 = new ArrayList<>();
        List<Food> foods3 = new ArrayList<>();
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
                    foodDateSum2.setFoodList(foods2);
                }if(foodDate.getDay() == 2){
                    foodDateSum3.setDay(foodDate.getDay());
                    Food food = foodMapper.selFoodById(foodDate.getFoodId());
                    foods3.add(food);
                    foodDateSum3.setFoodList(foods3);
                }
                foodDateSum.setFoodList(foods);
                foodDateSum2.setFoodList(foods2);
                foodDateSum3.setFoodList(foods3);
            }

        }


//        foodDateSumList.add(foodDateSum);


        foodDateSumList.add(foodDateSum);
        foodDateSumList.add(foodDateSum2);
        foodDateSumList.add(foodDateSum3);
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

