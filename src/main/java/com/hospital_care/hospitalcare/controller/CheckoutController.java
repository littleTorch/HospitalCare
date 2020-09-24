package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Bed;
import com.hospital_care.hospitalcare.entity.Checkin;
import com.hospital_care.hospitalcare.entity.Checkout;
import com.hospital_care.hospitalcare.entity.Customer;
import com.hospital_care.hospitalcare.entity.bed.BedVo;
import com.hospital_care.hospitalcare.entity.checkout.CheckoutAddVo;
import com.hospital_care.hospitalcare.entity.checkout.CheckoutEditVo;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.BedService;
import com.hospital_care.hospitalcare.service.CheckinService;
import com.hospital_care.hospitalcare.service.CheckoutService;
import com.hospital_care.hospitalcare.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/checkout")
public class CheckoutController {

    @Autowired
    private CheckinService checkinService;
    @Autowired
    private CheckoutService checkoutService;
    @Autowired
    private BedService bedService;
    @Autowired
    private CustomerService customerService;

    @ApiOperation("获取-t_customer-表信息")
    @GetMapping("/getCustomerList")
    public List<Customer> index(){
        List<Checkin> checkinList = checkinService.list();
        List<Integer> cusIds = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            cusIds.add(checkin.getCusId());
        }
        return customerService.list(Wrappers.<Customer>lambdaQuery().in(Customer::getId, cusIds).eq(Customer::getIsDeleted, 0));
    }

    @ApiOperation("获取入住表床位信息")
    @GetMapping("/getBedList")
    public List<BedVo> getBedList(){
        List<Checkin> checkinList = checkinService.list();
        List<String> bedIds = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            bedIds.add(checkin.getBedId());
        }
        List<Bed> bedList = bedService.list(Wrappers.<Bed>lambdaQuery().in(Bed::getId, bedIds).eq(Bed::getIsDeleted, 0));
        List<BedVo> bedVoList = new ArrayList<>();
        for (Bed bed : bedList) {
            BedVo bedVo = new BedVo();
            bedVo.setValue(bed.getFloor());
            bedVo.setLabel(bed.getFloor()+"楼");
            bedVo.setBed(bed);
            bedVoList.add(bedVo);
        }
        bedList.sort((o1,o2) -> {
            return o1.getRoomNo().compareTo(o2.getRoomNo());
        });
        Set set = new HashSet();
        set.addAll(bedVoList);
        List<BedVo> bedVoList2 = new ArrayList<>();
        bedVoList2.addAll(set);
        for (BedVo bedVo : bedVoList2) {
            List<BedVo> children = new ArrayList<>();
            bedVo.setChildren(children);
            BedVo temp = null;
            for (BedVo vo : bedVoList) {
                if (vo.getValue().equals(bedVo.getValue())){
                    BedVo bedVo1 = new BedVo();
                    bedVo1.setValue(vo.getBed().getRoomNo());
                    bedVo1.setLabel(vo.getBed().getRoomNo()+"号");
                    if (temp==null){
                        temp = bedVo1;
                    }

                    List<BedVo> children2 = null;

                    if (temp.getChildren()!=null && temp.getValue().equals(bedVo1.getValue())){
                        children2 = temp.getChildren();
                        BedVo bedVo2 = new BedVo();
                        bedVo2.setValue(vo.getBed().getBedNo());
                        bedVo2.setLabel(vo.getBed().getBedNo()+"号床");
                        children2.add(bedVo2);
                    } else {
                        children2 = new ArrayList<>();
                        bedVo1.setChildren(children2);
                        temp = bedVo1;
                        BedVo bedVo2 = new BedVo();
                        bedVo2.setValue(vo.getBed().getBedNo());
                        bedVo2.setLabel(vo.getBed().getBedNo()+"号床");
                        children2.add(bedVo2);
                        children.add(bedVo1);
                    }
                }
            }
            Set set2 = new HashSet();
            set2.addAll(children);
            children.clear();
            children.addAll(set2);
            children.sort((o1,o2) -> {
                return o1.getValue().compareTo(o2.getValue());
            });
        }
        return bedVoList2;
    }

    @ApiOperation("点击添加获取退住登记整合信息")
    @GetMapping("/getCheckoutAddVoList")
    public List<CheckoutAddVo> getCheckoutAddVoList(){
        List<Checkin> checkinList = checkinService.list();
        List<CheckoutAddVo> checkoutAddVoList = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            CheckoutAddVo checkoutAddVo = new CheckoutAddVo();
            Customer customer = customerService.getById(checkin.getCusId());
            Bed bed = bedService.getById(checkin.getBedId());
            checkoutAddVo.setCusId(customer.getId());
            checkoutAddVo.setCusName(customer.getCusName());
            checkoutAddVo.setBedId(bed.getId());
            checkoutAddVo.setFloor(bed.getFloor());
            checkoutAddVo.setRoomNo(bed.getRoomNo());
            checkoutAddVo.setBedNo(bed.getBedNo());
            checkoutAddVo.setLabel("客户名："+checkoutAddVo.getCusName()+"\n"
                    +"楼层："+checkoutAddVo.getFloor()+"楼\n"
                    +"房间号："+checkoutAddVo.getRoomNo()+"号\n"
                    +"床位号："+checkoutAddVo.getBedNo()+"号");
            checkoutAddVoList.add(checkoutAddVo);
        }
        return checkoutAddVoList;
    }

    @ApiOperation("分页获取退住客户信息")
    @GetMapping("/getCheckoutList")
    public ResultVo getCheckoutList(ParmVo parm){
        List<Checkin> checkinList = checkinService.list();
        List<Integer> cusIds = new ArrayList<>();
        List<Bed> bedList = new ArrayList<>();
        for (Checkin checkin : checkinList) {
            cusIds.add(checkin.getCusId());
            Bed bed = bedService.getById(checkin.getBedId());
            bed.setCusId(checkin.getCusId());
            bedList.add(bed);
        }

        List<Customer> customerList = customerService.list(Wrappers.<Customer>lambdaQuery().in(Customer::getId, cusIds).eq(Customer::getIsDeleted, 0));
        IPage<Checkout> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<Checkout> checkoutIPage = checkoutService.page(page, Wrappers.<Checkout> lambdaQuery().in(Checkout::getCusId, cusIds));
        List<Checkout> records = checkoutIPage.getRecords();

        for (Bed bed : bedList) {
            for (int i = 0; i < records.size(); i++) {
                if (bed.getCusId().equals(records.get(i).getCusId())){
                    records.get(i).setBed(bed);
                    break;
                }
            }
        }

        for (Customer customer : customerList) {
            for (int i = 0; i < records.size(); i++) {
                if (customer.getId().equals(records.get(i).getCusId())){
                    records.get(i).setCustomer(customer);
                    break;
                }
            }
        }

        return ResultUtils.success("成功", checkoutIPage);
    }

    @ApiOperation("新增一条退住登记信息")
    @PostMapping("/addCheckout")
    public ResultVo addCheckout(Checkout checkout){
        boolean save = checkoutService.save(checkout);
        if (save){
            return ResultUtils.success("登记成功");
        }
        return ResultUtils.error("登记失败");
    }

    @ApiOperation("编辑一个退住信息")
    @PostMapping("/checkoutEdit")
    //public ResultVo checkoutEdit(CheckoutVo checkoutEditVo, Customer customer, Bed bed){
    public ResultVo checkoutEdit(CheckoutEditVo checkoutEditVo){
        Checkout checkout = new Checkout();
        checkout.setId(checkoutEditVo.getId());
        checkout.setCreateTime(checkoutEditVo.getCreateTime());
        checkout.setCreateBy(checkoutEditVo.getCreateBy());
        checkout.setUpdateBy(checkoutEditVo.getUpdateBy());
        checkout.setCusId(checkoutEditVo.getCusId());
        Customer customer = new Customer();
        customer.setId(checkoutEditVo.getCusId());
        customer.setUpdateBy(checkoutEditVo.getUpdateBy());
        customer.setCusName(checkoutEditVo.getCusName());
        Bed bed = new Bed();
        bed.setId(checkoutEditVo.getBedId());
        bed.setUpdateBy(checkoutEditVo.getUpdateBy());
        bed.setFloor(checkoutEditVo.getFloor());
        bed.setRoomNo(checkoutEditVo.getRoomNo());
        bed.setBedNo(checkoutEditVo.getBedNo());
        checkout.setCheckoutDate(checkoutEditVo.getCheckoutDate());
        checkout.setCheckoutType(checkoutEditVo.getCheckoutType());
        checkout.setCheckoutReason(checkoutEditVo.getCheckoutReason());
        checkout.setStatus(checkoutEditVo.getStatus());
        checkout.setAuditPerson(checkoutEditVo.getAuditPerson());
        checkout.setAudit(checkoutEditVo.getAudit());
        checkout.setAuditTime(checkoutEditVo.getAuditTime());
        boolean update = checkoutService.update(checkout, Wrappers.<Checkout>lambdaQuery().eq(Checkout::getId, checkout.getId()).eq(Checkout::getIsDeleted, 0));
        boolean update2 = customerService.update(customer, Wrappers.<Customer>lambdaQuery().eq(Customer::getId, customer.getId()).eq(Customer::getIsDeleted, 0));
        boolean update3 = bedService.update(bed, Wrappers.<Bed>lambdaQuery().eq(Bed::getId, bed.getId()).eq(Bed::getIsDeleted, 0));
        if (update && update2 && update3){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    @ApiOperation("审核一条退住信息")
    @PostMapping("/checkoutAuditUpdate")
    public ResultVo checkoutAuditUpdate(Checkout checkout){
        boolean update = checkoutService.update(checkout, Wrappers.<Checkout>lambdaQuery().eq(Checkout::getId, checkout.getId()).eq(Checkout::getIsDeleted, 0));
        if (update){
            return ResultUtils.success("审核成功");
        }
        return ResultUtils.error("审核失败");
    }

    @ApiOperation("退住一条退住登记")
    @PostMapping("/checkoutDo")
    public ResultVo checkoutDo(Checkout checkout){
        boolean update = checkoutService.update(checkout, Wrappers.<Checkout>lambdaQuery().eq(Checkout::getId, checkout.getId()).eq(Checkout::getIsDeleted, 0));
        if (update){
            return ResultUtils.success("退住成功");
        }
        return ResultUtils.error("退住失败");
    }

    @ApiOperation("模糊查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike(ParmVo parm, String cusName){
        ResultVo checkoutList = getCheckoutList(parm);
        IPage<Checkout> checkoutIPage = (IPage<Checkout>) checkoutList.getData();
        List<Customer> customerList = customerService.list(Wrappers.<Customer>lambdaQuery().like(Customer::getCusName, cusName));
        List<Checkout> checkouts = new ArrayList<>();
        for (Customer customer : customerList) {
            for (Checkout checkout : checkoutIPage.getRecords()) {
                if (customer.getId().equals(checkout.getCusId())){
                    checkouts.add(checkout);
                    break;
                }
            }
        }

        checkoutIPage.setRecords(checkouts);
        checkoutIPage.setTotal(checkouts.size());
        return ResultUtils.success("查询成功",checkoutIPage);
    }

    @ApiOperation("删除一条退住表信息")
    @DeleteMapping("/delCheckout")
    public ResultVo delCheckout(Checkout checkout){
        boolean b = checkoutService.removeById(checkout.getId());
        if (b){
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    @ApiOperation("批量删除退住表信息")
    @DeleteMapping(value = "/delSelected",produces = {"application/json;charset=utf-8"})
    public ResultVo delete(@RequestBody String[] ids){
        boolean b = false;
        for(String id : ids){
            b = checkoutService.removeById(id);
        }
        if (b) {
            return ResultUtils.success("批量删除成功");
        }
        return ResultUtils.error("批量删除失败");
    }


}

