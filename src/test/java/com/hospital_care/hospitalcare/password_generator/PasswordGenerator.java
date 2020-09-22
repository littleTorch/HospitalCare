package com.hospital_care.hospitalcare.password_generator;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.mapper.FoodDateMapper;
import com.hospital_care.hospitalcare.mapper.FoodManageMapper;
import com.hospital_care.hospitalcare.mapper.FoodMapper;
import com.hospital_care.hospitalcare.service.FoodService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PasswordGenerator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用来生成加密后的密码，用于手动填入数据库
     */
    @Test
    public void getEncodePassword() {
        String encode = passwordEncoder.encode("123456");
        System.out.println("生成的密码为：" + encode);
    }

    @Test
    public void isPassWordTrue(){
        boolean matches = passwordEncoder.matches("123456", "$2a$10$WK3SjM1S0j5rSh1.3u2iruLWVxoUiX1Ve76cBAMO9S2rre4/mqJzS");
        System.out.println("密码正确？"+matches);
    }

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private FoodManageMapper foodManageMapper;

    @Resource
    private FoodDateMapper foodDateMapper;

    @Resource
    private FoodMapper foodMapper;

    @Test
    public void aaa(){

        QueryWrapper<Customer> wrapper1 = new QueryWrapper<>();
        wrapper1.like("record_id", "xxx001");
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
        System.out.println(foodDateSumList);

    }

    @Test
    public void  xx(){
        QueryWrapper<FoodDate> wrapper2 = new QueryWrapper<>();
        wrapper2.like("food_id","18").like("date",
                "0").like("day","0");
        FoodDate foodDate = foodDateMapper.selectOne(wrapper2);
        System.out.println(foodDate);
    }
    

}
