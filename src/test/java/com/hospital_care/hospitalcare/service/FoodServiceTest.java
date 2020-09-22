package com.hospital_care.hospitalcare.service;

import com.hospital_care.hospitalcare.entity.Food;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodServiceTest {

    @Autowired
    private FoodService foodService;

    @Test
    public void getList(){
        List<Food> list = foodService.findFoodList();
        list.forEach(System.out::println);
    }

}
