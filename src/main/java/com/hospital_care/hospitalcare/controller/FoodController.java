package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.Food;
import com.hospital_care.hospitalcare.mapper.FoodMapper;
import com.hospital_care.hospitalcare.result.ResultVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/food")
public class FoodController {


    @Resource
    private FoodMapper foodMapper;

    @ApiOperation("查询所有食物")
    @PostMapping("/foodList")
    public ResultVo foodList(){
        List<Food> foods = foodMapper.selFoodList();
        //foods.forEach(System.out::println);
        return new ResultVo("查询成功",200,foods);
    }

    @ApiOperation("模糊查询食物")
    @PostMapping("/selectByLike")
    public ResultVo selectByLike(String foodName){
        System.out.println(foodName);
        List<Food> foods = foodMapper.selLike(foodName);
        return new ResultVo("查询成功",200,foods);
    }

    @ApiOperation("增加一种食物")
    @PostMapping("/addOneFood")
    public ResultVo addOneFood(Food food){
        //System.out.println(food);
        int insert = foodMapper.insert(food);
        if (insert>0) {
            return new ResultVo("增加成功", 200, null);
        }else {
            return new ResultVo("增加失败", 500, null);
        }
    }

    @ApiOperation("删除一种食物")
    @PostMapping("/delOneFood")
    public ResultVo delOneFood(String foodId){
        //System.out.println(foodId);
        int insert = foodMapper.delFood(foodId);
        if (insert>0) {
            return new ResultVo("删除成功", 200, null);
        }else {
            return new ResultVo("删除失败", 500, null);
        }
    }

    @ApiOperation("批量删除食物")
    @DeleteMapping(value = "/delete",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        int i = 0;
        for(String id:ids){

            i = foodMapper.delFood(id);
        }
        if (i>0) {
            return new ResultVo("批量删除成功", 200, null);
        }else {
            return new ResultVo("批量删除失败", 500, null);
        }
    }

    @ApiOperation("查询一种食物")
    @GetMapping("/findOneFood")
    public ResultVo findOneFood(String foodId){
        //System.out.println(foodId);
        Food food = foodMapper.findFoodById(foodId);
        return new ResultVo("查询成功", 200, food);

    }

    @ApiOperation("修改一种食物")
    @PostMapping("/updateOneFood")
    public ResultVo updateOneFood(Food food){
        System.out.println(food);
        int i = foodMapper.updateFood(food);
        if (i>0) {
            return new ResultVo("修改成功", 200, null);
        }else {
            return new ResultVo("修改失败", 500, null);
        }

    }

}

