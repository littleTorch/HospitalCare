package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.NurseLevel;
import com.hospital_care.hospitalcare.entity.NurseProject;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.mapper.NurseLevelMapper;
import com.hospital_care.hospitalcare.mapper.NurseProjectMapper;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.NurseLevelService;
import com.hospital_care.hospitalcare.service.NurseProjectService;
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
@RequestMapping("/nurseProject")
public class NurseProjectController {

    @Resource
    private NurseProjectMapper nurseProjectMapper;
    @Autowired
    private NurseProjectService nurseProjectService;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");

    @ApiOperation("获取-nurseProject-表信息")
    @GetMapping("/nurseProjectList")
    public ResultVo index(){
        List<NurseProject> nurseProjectList = nurseProjectMapper.selectList(null);
        return ResultUtils.success("查询成功",nurseProjectList);
    }

    @ApiOperation("根据id查询")
    @GetMapping("/selectById")
    public ResultVo selectById(int id){
        QueryWrapper<NurseProject> wrapper = new QueryWrapper<>();
        wrapper.eq("id",id);
        NurseProject nurseProjects = nurseProjectMapper.selectOne(wrapper);
        return ResultUtils.success("查询成功",nurseProjects);
    }

    @ApiOperation("后端分页获取-nurseProject-表信息")
    @GetMapping("/getNurseProjectList")
    public ResultVo getNurseProjectList(ParmVo parmVo){
        IPage<NurseProject> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<NurseProject> valuablesIPage = nurseProjectService.page(page);
        return ResultUtils.success("查询成功", valuablesIPage);
    }

    @ApiOperation("新增护理等级信息")
    @PostMapping("/insertOne")
    public ResultVo insertOne(NurseProject nurseProject){
        int count = nurseProjectMapper.insert(nurseProject);
        if (count > 0){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }

    @ApiOperation("修改护理等级信息")
    @PutMapping("/updateOne")
    public ResultVo updateOne(NurseProject nurseProject){
        int count = nurseProjectMapper.updateById(nurseProject);
        if (count > 0){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    @ApiOperation("删除护理等级信息")
    @DeleteMapping("/deleteOne")
    public ResultVo deleteOne(int id){
        int count = nurseProjectMapper.deleteById(id);
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
            int i = nurseProjectMapper.deleteById(id);
            cout+=i;
        }
        if (cout > 0) {
            return ResultUtils.success("批量删除成功", cout);
        }
        return ResultUtils.error("批量删除失败");
    }

    @ApiOperation("后端分页级别查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike2(ParmVo parmVo, String projectName){
        IPage<NurseProject> page = new Page();
        QueryWrapper<NurseProject> wrapper = new QueryWrapper<>();
        wrapper.like("project_name",projectName);
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<NurseProject> valuablesIPage = nurseProjectService.page(page,wrapper);
        return ResultUtils.success("查询成功", valuablesIPage);
    }
}

