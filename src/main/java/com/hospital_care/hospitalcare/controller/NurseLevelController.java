package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.NurseLevel;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.mapper.NurseLevelMapper;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.NurseLevelService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
@RequestMapping("/nurseLevel")
public class NurseLevelController {

    @Resource
    private NurseLevelMapper nurseLevelMapper;
    @Autowired
    private NurseLevelService nurseLevelService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @ApiOperation("获取-nurseLevel-表信息")
    @GetMapping("/nurseLevelList")
    public List<NurseLevel> index(){
        List<NurseLevel> nurseLevels = nurseLevelMapper.selectList(null);
        return nurseLevels;
    }

    @ApiOperation("后端分页获取-nurseLevel-表信息")
    @GetMapping("/getNurseLevelList")
    public ResultVo getNurseLevelList(ParmVo parmVo){
        IPage<NurseLevel> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<NurseLevel> valuablesIPage = nurseLevelService.page(page);
        return ResultUtils.success("查询成功", valuablesIPage);
    }

    @ApiOperation("新增护理等级信息")
    @PostMapping("/insertOne")
    public ResultVo insertOne(NurseLevel nurseLevel){
        int count = nurseLevelMapper.insert(nurseLevel);
        if (count > 0){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }

    @ApiOperation("修改护理等级信息")
    @PostMapping("/updateOne")
    public ResultVo updateOne(NurseLevel nurseLevel){
        int count = nurseLevelMapper.updateById(nurseLevel);
        if (count > 0){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    @ApiOperation("删除护理等级信息")
    @GetMapping("/deleteOne")
    public ResultVo deleteOne(int valId){
        int count = nurseLevelMapper.deleteById(valId);
        if (count > 0){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    @ApiOperation("批量删除物品等级信息")
    @DeleteMapping(value = "/delSelected",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        System.out.println(Arrays.toString(ids));
        int cout = 0;
        for(String id : ids){
            int i = nurseLevelMapper.deleteById(id);
            cout+=i;
        }
        if (cout > 0) {
            return ResultUtils.success("批量删除成功", cout);
        }
        return ResultUtils.error("批量删除失败");
    }

    @ApiOperation("根据级别查询")
    @GetMapping("/selectByLike")
    public List<NurseLevel> selectByLike(int level){
        LambdaQueryWrapper<NurseLevel> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.eq(NurseLevel::getLevel, level);
        List<NurseLevel> nurseLevels = nurseLevelMapper.selectList(lambdaQuery);
        return nurseLevels;
    }

    @ApiOperation("后端分页级别查询")
    @GetMapping("/selectByLike2")
    public ResultVo selectByLike2(ParmVo parmVo,int level){
        IPage<NurseLevel> page = new Page();
        QueryWrapper<NurseLevel> wrapper = new QueryWrapper<>();
        wrapper.eq("level",level);
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<NurseLevel> valuablesIPage = nurseLevelService.page(page,wrapper);
        return ResultUtils.success("查询成功", valuablesIPage);
    }
}

