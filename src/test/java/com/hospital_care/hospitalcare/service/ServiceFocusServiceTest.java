package com.hospital_care.hospitalcare.service;

import com.alibaba.fastjson.JSON;
import com.hospital_care.hospitalcare.controller.ServiceFocusController;
import com.hospital_care.hospitalcare.entity.ServiceFocus;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceFocusServiceTest {

    @Autowired
    private ServiceFocusController serviceFocusController;
    @Autowired
    private ServiceFocusService serviceFocusService;

    @Test
    public void findServiceFocusListByCustomerIdList(){
        List<ServiceFocus> serviceFocusListByCustomerIdList = serviceFocusService.findServiceFocusListIfRecordIdNotEmpty();
        serviceFocusListByCustomerIdList.forEach(System.out::println);
    }

    @Test
    public void getServiceFocusList(){
        ParmVo parmVo = new ParmVo();
        parmVo.setCurrentPage(1);
        parmVo.setPageSize(10);
        ResultVo serviceFocusList = serviceFocusController.getServiceFocusList(parmVo);
        String s = JSON.toJSONString(serviceFocusList);
        System.out.println(s);
    }

}
