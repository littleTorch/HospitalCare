package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.Utils.StrDateSDF;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.jwt.JwtUtils;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.CustomerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Arrays;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@Api(tags = "客户表 API")
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("后端分页获取-t_customer-表信息")
    @GetMapping("/getCustomerList")
    public ResultVo index(ParmVo parm){
        IPage<Customer> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<Customer> userList = customerService.page(page);
        return ResultUtils.success("成功", userList);
    }

    @ApiOperation("查询Customer列表")
    @GetMapping("/selectCustomerList")
    public ResultVo selectRoomList(String cusName, String recordId,Long pageSize, Long currentPage){
        QueryWrapper<Customer> wrapper=new QueryWrapper<>();
        if (!recordId.equals(""))
            wrapper.like("record_id",recordId);
        if (!cusName.equals(""))
            wrapper.like("cus_name",cusName);
        IPage<Customer> iPage=new Page<>();
        iPage.setCurrent(currentPage);
        iPage.setSize(pageSize);
        IPage<Customer> page = customerService.page(iPage, wrapper);
        return ResultUtils.success("查询成功",page);
    }

    @ApiOperation("插入一条-customer-表信息")
    @PostMapping(value="/addOne",produces = {"application/json;charset=utf-8"})
    public ResultVo addOne(@RequestBody Customer customer, HttpServletRequest request) throws ParseException {
        customer.setExpirationDate(StrDateSDF.strToDate(customer.getExpirationDate()));
        customer.setBirthday(StrDateSDF.strToDate(customer.getBirthday()));
        customer.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        return customerService.save(customer)? ResultUtils.success("新增成功") : ResultUtils.success("新增失败") ;
    }

    @ApiOperation("更新一条-customer-表信息")
    @PutMapping("/updateOne")
    public ResultVo updateOneRoom(@RequestBody Customer customer,HttpServletRequest request) throws ParseException {
        customer.setExpirationDate(StrDateSDF.strToDate(customer.getExpirationDate()));
        customer.setBirthday(StrDateSDF.strToDate(customer.getBirthday()));
        customer.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        return customerService.updateById(customer)? ResultUtils.success("修改成功") : ResultUtils.success("修改失败");
    }

    @ApiOperation("删除多条-customer-表信息")
    @DeleteMapping(value = "/dels",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        return customerService.removeByIds(Arrays.asList(ids)) ? ResultUtils.success("删除成功") : ResultUtils.success("删除失败") ;
    }
}

