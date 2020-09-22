package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.Utils.StrDateSDF;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.entity.Checkin;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.jwt.JwtUtils;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.BedService;
import com.hospital_care.hospitalcare.service.CheckinService;
import com.hospital_care.hospitalcare.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-15
 */
@RestController
@RequestMapping("/checkin")
public class CheckinController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private BedService bedService;
    @Autowired
    private CheckinService checkinService;
    @Autowired
    private JwtUtils jwtUtils;

    @ApiOperation("查询checkin列表")
    @GetMapping("/selectList")
    public ResultVo selectList(String cusId, String roomNo, Long pageSize, Long currentPage){
        QueryWrapper<Customer> cusWrapper=new QueryWrapper<>();
        if (!cusId.equals(""))
            cusWrapper.eq("id",cusId);
        List<Customer> cusList = customerService.list(cusWrapper);
        List<Integer> cusIds=new ArrayList<>();
        for (Customer customer : cusList) {
            cusIds.add(customer.getId());
        }
        if (cusIds.size()==0) return ResultUtils.error("查询失败");
        QueryWrapper<Bed> bedWrapper=new QueryWrapper<>();
        bedWrapper.like("room_No",roomNo);
        List<Bed> bedList = bedService.list(bedWrapper);
        List<Integer> bedIds=new ArrayList<>();
        for (Bed bed : bedList) {
            bedIds.add(bed.getId());
        }

        QueryWrapper<Checkin> wrapper=new QueryWrapper<>();
        wrapper.in("cus_id",cusIds);
        wrapper.in("bed_id",bedIds);
        IPage<Checkin> iPage=new Page<>();
        iPage.setCurrent(currentPage);
        iPage.setSize(pageSize);
        IPage<Checkin> page = checkinService.page(iPage, wrapper);
        List<Checkin> records = page.getRecords();
        for (int i = 0; i < records.size(); i++) {
            Checkin checkin = records.get(i);
            checkin.setBed(bedService.getById(checkin.getBedId()));
            Customer customer = customerService.getById(checkin.getCusId());
            checkin.setCustomer(customer);
            records.set(i,checkin);
        }
        return ResultUtils.success("查询成功",page);
    }

    @ApiOperation("查询bed列表")
    @GetMapping("/selectBed")
    public List<Bed> selBed(){
        List<Bed> list = bedService.list();
        List<Bed> bedlist=new ArrayList<>();
        HashMap<String, Bed> map = new HashMap<String, Bed>();
        for (Bed bed : list) {
            map.put(bed.getRoomNo(),bed);
        }
        Collection<Bed> values = map.values();
        for (Bed value : values) {
            bedlist.add(value);
        }
        return bedlist;
    }

    @ApiOperation("查询bed列表")
    @GetMapping("/selBed")
    public List<Bed> sel(String roomNo){
        QueryWrapper<Bed> queryWrapper = new QueryWrapper<Bed>().eq("room_no", roomNo);
        return bedService.list(queryWrapper);
    }

    @ApiOperation("查询cus列表")
    @GetMapping("/selcus")
    public List<Customer> sel1(){
        return customerService.list();
    }



    @ApiOperation("插入一条-checkin-表信息")
    @PostMapping("/addOne")
    public ResultVo addOneRoom(Checkin checkin, HttpServletRequest request){
        checkin.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        checkin.setCheckinDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        return checkinService.save(checkin)? ResultUtils.success("新增成功") : ResultUtils.success("新增失败") ;
    }

    @ApiOperation("更新一条-checkin-表信息")
    @PutMapping("/updateOne")
    public ResultVo updateOneRoom(@RequestBody Checkin checkin,String bedId,HttpServletRequest request) throws ParseException {
        System.out.println(checkin);
        checkin.setUpdateBy(jwtUtils.getUsernameFromToken(request.getHeader("token")));
        checkin.setCheckinDate(StrDateSDF.strToDate(checkin.getCheckinDate()));
        checkin.setBedId(bedId);
        return checkinService.updateById(checkin)? ResultUtils.success("修改成功") : ResultUtils.success("修改失败");
    }

    @ApiOperation("删除多条-checkin-表信息")
    @DeleteMapping(value = "/dels",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        return checkinService.removeByIds(Arrays.asList(ids)) ? ResultUtils.success("删除成功") : ResultUtils.success("删除失败") ;
    }
}

