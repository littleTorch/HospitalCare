package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.CustomerService;
import com.hospital_care.hospitalcare.service.ServiceObjService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@Api(tags = "设置服务对象 API")
@RestController
@RequestMapping("/serviceObj")
public class ServiceObjController {

    @Autowired
    private CustomerService customerService;

    @ApiOperation("后端分页获取-t_customer-表信息")
    @GetMapping("/getCustomerList")
    public ResultVo index(ParmVo parm){
        IPage<Customer> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<Customer> customerIPage = customerService.page(page, Wrappers.<Customer>lambdaQuery().ne(Customer::getRecordId, null).or().ne(Customer::getRecordId, ""));
        return ResultUtils.success("成功", customerIPage);
    }

    @ApiOperation("获取一个customer信息")
    @GetMapping("/getCustomer")
    public Customer getCustomer(String id){
        return customerService.getById(id);
    }

    @ApiOperation("更新一个客户Customer信息")
    @PostMapping("/updateCustomer")
    public ResultVo updateCustomer(Customer customer){
        boolean update = customerService.updateById(customer);
        if (update) {
            return ResultUtils.success("更新成功");
        } else {
            return ResultUtils.error("更新失败");
        }
    }

    @ApiOperation("根据客户id删除")
    @GetMapping("/deleteCustomerById")
    public ResultVo deleteCustomerById(String id){
        boolean b = customerService.removeById(id);
        if(b){
            return ResultUtils.success("删除客户成功");
        }else{
            return ResultUtils.error("删除客户失败");
        }
    }

    @ApiOperation("模糊查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike(Customer customer){
        List<Customer> list = customerService.list(Wrappers.<Customer>lambdaQuery().like(Customer::getCusName, customer.getCusName()));
        return ResultUtils.success("查询成功",list);
    }

}

