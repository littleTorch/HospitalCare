package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.BedService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/bed")
public class BedController {

    @Autowired
    BedService bedService;

    @ApiOperation("分配权限树查询")
    @GetMapping("/selectBedList")
    public ResultVo selectRoomList(String floor, String roomNo,Long pageSize, Long currentPage){
        QueryWrapper<Bed> wrapper=new QueryWrapper<>();
        wrapper.like("floor",floor).like("room_no",roomNo);
        IPage<Bed> iPage=new Page<>();
        iPage.setCurrent(currentPage);
        iPage.setSize(pageSize);
        IPage<Bed> page = bedService.page(iPage, wrapper);
        return ResultUtils.success("查询成功",page);
    }

}

