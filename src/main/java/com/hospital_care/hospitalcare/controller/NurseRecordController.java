package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.mapper.EmpMapper;
import com.hospital_care.hospitalcare.mapper.NurseProjectMapper;
import com.hospital_care.hospitalcare.mapper.NurseRecordMapper;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.NurseProjectService;
import com.hospital_care.hospitalcare.service.NurseRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
@RequestMapping("/nurseRecord")
public class NurseRecordController {


    @Resource
    private NurseRecordMapper nurseRecordMapper;
    @Autowired
    private NurseRecordService nurseRecordService;

    @Resource
    private NurseProjectMapper nurseProjectMapper;
    @Resource
    private EmpMapper empMapper;
    @Resource
    private CustomerMapper customerMapper;

    @ApiOperation("获取-nurseRecord-表信息")
    @GetMapping("/nurseRecordList")
    public ResultVo index(){
        List<NurseRecord> nurseRecordList = nurseRecordMapper.selectList(null);
        return ResultUtils.success("查询成功",nurseRecordList);
    }

    @ApiOperation("获取-护理人员")
    @GetMapping("/getEmpName")
    public ResultVo getEmpName(){
        QueryWrapper<Emp> wrapper = new QueryWrapper<>();
        wrapper.eq("emp_type",2);
        List<Emp> empList = empMapper.selectList(wrapper);
        return ResultUtils.success("查询成功",empList);
    }

    @ApiOperation("后端分页获取-nurseRecord-表信息")
    @GetMapping("/getNurseRecordList")
    public ResultVo getNurseRecordList(ParmVo parmVo){
        IPage<NurseRecord> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        List<NurseProject> nurseProjectList = nurseProjectMapper.selectList(null);
        List<Emp> empList = empMapper.selectList(null);
        List<Customer> customers = customerMapper.selectList(null);
        IPage<NurseRecord> valuablesIPage = nurseRecordService.page(page);
        List<NurseRecord> records = valuablesIPage.getRecords();
        for(int i = 0 ;i< records.size();i++){
            for (NurseProject nurseProject: nurseProjectList){
                if (records.get(i).getProjectId()==nurseProject.getId()){
                    records.get(i).setProjectName(nurseProject.getProjectName());
                }
            }
            for (Customer customer: customers){
                if (records.get(i).getCusId()==customer.getId()){
                    records.get(i).setCusName(customer.getCusName());
                }
            }
            for (Emp emp: empList){
                if (records.get(i).getEmpId()==emp.getId()){
                    records.get(i).setEmpName(emp.getEmpName());
                }
            }
        }
        return ResultUtils.success("查询成功", valuablesIPage);
    }

    @ApiOperation("新增护理记录信息")
    @PostMapping("/insertOne")
    public ResultVo insertOne(NurseRecord nurseRecord){
        int count = nurseRecordMapper.insert(nurseRecord);
        if (count > 0){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }

    @ApiOperation("修改护理记录信息")
    @PutMapping("/updateOne")
    public ResultVo updateOne(NurseRecord nurseRecord){
        int count = nurseRecordMapper.updateById(nurseRecord);
        if (count > 0){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    @ApiOperation("删除护理等级信息")
    @DeleteMapping("/deleteOne")
    public ResultVo deleteOne(int id){
        int count = nurseRecordMapper.deleteById(id);
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
            int i = nurseRecordMapper.deleteById(id);
            cout+=i;
        }
        if (cout > 0) {
            return ResultUtils.success("批量删除成功", cout);
        }
        return ResultUtils.error("批量删除失败");
    }

    @ApiOperation("后端分页级别查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike2(ParmVo parmVo, int cusId){
        IPage<NurseRecord> page = new Page();
        QueryWrapper<NurseRecord> wrapper = new QueryWrapper<>();
        wrapper.like("cus_id",cusId);
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<NurseRecord> valuablesIPage = nurseRecordService.page(page,wrapper);
        List<NurseProject> nurseProjectList = nurseProjectMapper.selectList(null);
        List<Emp> empList = empMapper.selectList(null);
        List<Customer> customers = customerMapper.selectList(null);
        List<NurseRecord> records = valuablesIPage.getRecords();
        for(int i = 0 ;i< records.size();i++){
            for (NurseProject nurseProject: nurseProjectList){
                if (records.get(i).getProjectId()==nurseProject.getId()){
                    records.get(i).setProjectName(nurseProject.getProjectName());
                }
            }
            for (Customer customer: customers){
                if (records.get(i).getCusId()==customer.getId()){
                    records.get(i).setCusName(customer.getCusName());
                }
            }
            for (Emp emp: empList){
                if (records.get(i).getEmpId()==emp.getId()){
                    records.get(i).setEmpName(emp.getEmpName());
                }
            }
        }
        return ResultUtils.success("查询成功", valuablesIPage);
    }

}

