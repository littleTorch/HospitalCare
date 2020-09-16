package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.jwt.JwtUtils;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.BedService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

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
    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("查询Bed列表")
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

    @ApiOperation("插入一条-bed-表信息")
    @PostMapping("/addOneRoom")
    public ResultVo addOneRoom(Bed bed, HttpServletRequest request){
        bed.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        return bedService.save(bed)? ResultUtils.success("新增成功") : ResultUtils.success("新增失败") ;
    }

    @ApiOperation("更新一条-bed-表信息")
    @PutMapping("/updateOne")
    public ResultVo updateOneRoom(@RequestBody Bed bed,HttpServletRequest request){
        bed.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        return bedService.updateById(bed)? ResultUtils.success("修改成功") : ResultUtils.success("修改失败");
    }

    @ApiOperation("删除多条-bed-表信息")
    @DeleteMapping(value = "/dels",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        return bedService.removeByIds(Arrays.asList(ids)) ? ResultUtils.success("删除成功") : ResultUtils.success("删除失败") ;
    }
}

