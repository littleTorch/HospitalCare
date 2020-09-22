package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Checkin;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.CheckinService;
import com.hospital_care.hospitalcare.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckinService checkinService;
    @Autowired
    private CustomerService customerService;

    @ApiOperation("后端分页获取入住客户信息")
    @GetMapping("/getCustomerListInCheckin")
    public ResultVo index(ParmVo parm){
        IPage<Customer> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        List<Checkin> checkinList = checkinService.list();
        List<Integer> cusIds = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            cusIds.add(checkin.getCusId());
        }
        IPage<Customer> customerIPage = customerService.page(page, Wrappers.<Customer>lambdaQuery().in(Customer::getId, cusIds));
        return ResultUtils.success("成功", customerIPage);
    }

    /*@ApiOperation("后端分页获取-t_customer-表信息")
    @GetMapping("/getCustomerList")
    public ResultVo index(ParmVo parm){
        IPage<Customer> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<Customer> customerIPage = customerService.page(page, Wrappers.<Customer>lambdaQuery().ne(Customer::getRecordId, null).or().ne(Customer::getRecordId, ""));
        return ResultUtils.success("成功", customerIPage);
    }*/





}

