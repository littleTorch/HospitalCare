package com.hospital_care.hospitalcare.controller;

import com.alibaba.fastjson.JSON;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.entity.Checkin;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.mapper.BedMapper;
import com.hospital_care.hospitalcare.mapper.CheckinMapper;
import com.hospital_care.hospitalcare.service.CustomerService;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "图表 API")
@RestController
@RequestMapping("/echart")
public class EchartController {

    @Resource
    private BedMapper bedMapper;

    @Resource
    private CheckinMapper checkinMapper;

    @Resource
    private CustomerService customerService;

    @GetMapping("/echart1")
    public Object echart1(){

        Map<String,Object> map = new HashMap<>();
        List<Bed> beds = bedMapper.selectList(null);
        int size1 = beds.size();

        List<Checkin> checkins = checkinMapper.selectList(null);
        int size2 = checkins.size();

        //System.out.println(size1+" "+size2);

        int size3 = size1 - size2;

        map.put("p1",size3);
        map.put("p2",size2);

        // json字符串转java对象 JSON.parseObject("json字符串"，User.class)
        return JSON.toJSONString(map);

    }

    @GetMapping("/echart2")
    public Object echart2(){

        Map<String,Object> map = new HashMap<>();
        List<Checkin> checkins = checkinMapper.selectList(null);
        int size1 = checkins.size();

        List<Customer> list = customerService.list(null);
        int size2 = list.size();



        int size = size2 - size1;
        System.out.println(size1+" "+size2+" "+size);

        map.put("p1",size1);
        map.put("p2",size);

        // json字符串转java对象 JSON.parseObject("json字符串"，User.class)
        return JSON.toJSONString(map);

    }

}
