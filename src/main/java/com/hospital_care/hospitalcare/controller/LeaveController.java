package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.mapper.CustomerMapper;
import com.hospital_care.hospitalcare.mapper.LeaveMapper;
import com.hospital_care.hospitalcare.mapper.NurseProjectMapper;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.LeaveService;
import com.hospital_care.hospitalcare.service.NurseProjectService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/leave")
public class LeaveController {

    @Resource
    private LeaveMapper leaveMapper;

    @Autowired
    private LeaveService leaveService;

    @Resource
    private CustomerMapper customerMapper;

    @ApiOperation("获取-leave-表信息")
    @GetMapping("/leaveList")
    public ResultVo index(){
        List<Leave> leaveList = leaveMapper.selectList(null);
        return ResultUtils.success("查询成功",leaveList);
    }

    @ApiOperation("后端分页获取-leave-表信息")
    @GetMapping("/getLeaveList")
    public ResultVo getNurseRecordList(ParmVo parmVo){
        IPage<Leave> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        List<Customer> customers = customerMapper.selectList(null);
        IPage<Leave> valuablesIPage = leaveService.page(page);
        List<Leave> leaveList = valuablesIPage.getRecords();
        for(int i = 0 ;i< leaveList.size();i++){
            for (Customer customer: customers){
                if (leaveList.get(i).getCusId()==customer.getId()){
                    leaveList.get(i).setCusName(customer.getCusName());
                }
            }
        }
        return ResultUtils.success("查询成功", valuablesIPage);
    }

    @ApiOperation("新增护理记录信息")
    @PostMapping("/insertOne")
    public ResultVo insertOne(Leave leave){
        int count = leaveMapper.insert(leave);
        if (count > 0){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }

    @ApiOperation("修改护理记录信息")
    @PutMapping("/updateOne")
    public ResultVo updateOne(Leave leave){
        int count = leaveMapper.updateById(leave);
        if (count > 0){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    @ApiOperation("删除护理等级信息")
    @DeleteMapping("/deleteOne")
    public ResultVo deleteOne(int id){
        int count = leaveMapper.deleteById(id);
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
            int i = leaveMapper.deleteById(id);
            cout+=i;
        }
        if (cout > 0) {
            return ResultUtils.success("批量删除成功", cout);
        }
        return ResultUtils.error("批量删除失败");
    }

    @ApiOperation("后端分页id查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike(ParmVo parmVo,int cusId){
        IPage<Leave> page = new Page();
        QueryWrapper<Leave> wrapper = new QueryWrapper<>();
        wrapper.like("cus_id",cusId);
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        List<Customer> customers = customerMapper.selectList(null);
        IPage<Leave> valuablesIPage = leaveService.page(page,wrapper);
        List<Leave> leaveList = valuablesIPage.getRecords();
        for(int i = 0 ;i< leaveList.size();i++){
            for (Customer customer: customers){
                if (leaveList.get(i).getCusId()==customer.getId()){
                    leaveList.get(i).setCusName(customer.getCusName());
                }
            }
        }
        return ResultUtils.success("查询成功", valuablesIPage);
    }
}

