package com.hospital_care.hospitalcare.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hospital_care.hospitalcare.entity.Checkout;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CheckoutControllerTest {

    @Autowired
    private CheckoutController checkoutController;

    @Test
    public void getCheckoutList(){
        ParmVo parmVo = new ParmVo();
        parmVo.setPageSize(10);
        parmVo.setCurrentPage(1);
        ResultVo checkoutList = checkoutController.getCheckoutList(parmVo);
        IPage<Checkout> checkoutIPage = (IPage<Checkout>) checkoutList.getData();
        System.out.println(JSON.toJSON(checkoutIPage.getRecords()));
    }

}
