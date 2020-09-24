package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.*;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/serviceFocus")
public class ServiceFocusController {

    @Autowired
    private ServiceObjService serviceObjService;
    @Autowired
    private ServiceFocusService serviceFocusService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;
    @Autowired
    private NurseProjectService nurseProjectService;

    @ApiOperation("后端获取-t_customer-表信息")
    @GetMapping("/getCustomerList")
    public List<Customer> index(){
        return customerService.list(Wrappers.<Customer>lambdaQuery().ne(Customer::getRecordId, null).or().ne(Customer::getRecordId, "").eq(Customer::getIsDeleted,0));
    }

    @ApiOperation("分页查询服务关注的信息")
    @GetMapping("/getServiceFocusList")
    public ResultVo getServiceFocusList(ParmVo parm){
        IPage<ServiceFocus> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        List<Customer> customerList = customerService.list(Wrappers.<Customer>lambdaQuery().ne(Customer::getRecordId, null).or().ne(Customer::getRecordId, ""));
        List<Integer> cusIds = new ArrayList<>();
        customerList.forEach(x -> {
            cusIds.add(x.getId());
        });
        IPage<ServiceFocus> serviceFocusIPage = serviceFocusService.page(page, Wrappers.<ServiceFocus> lambdaQuery().in(ServiceFocus::getCusId, cusIds));
        List<ServiceFocus> records = serviceFocusIPage.getRecords();
        List<Integer> projectIds = new ArrayList<>();
        for (ServiceFocus record : records) {
            projectIds.add(record.getProjectId());
        }
        List<NurseProject> nurseProjectList = nurseProjectService.list(Wrappers.<NurseProject>lambdaQuery().in(NurseProject::getId, projectIds));
        for (int i = 0; i < records.size(); i++) {
            for (Customer customer : customerList) {
                if (records.get(i).getCusId().equals(customer.getId())){
                    records.get(i).setElderType(customer.getElderType());
                    records.get(i).setCusName(customer.getCusName());
                }
            }
            for (NurseProject nurseProject : nurseProjectList) {
                if (records.get(i).getProjectId().equals(nurseProject.getId())){
                    records.get(i).setProjectName(nurseProject.getProjectName());
                }
            }
        }
        return ResultUtils.success("成功", serviceFocusIPage);
    }

    @ApiOperation("根据id查询一个服务关注信息")
    @GetMapping("getServiceFocus")
    public ServiceFocus getServiceFocus(String id){
        return serviceFocusService.getById(id);
    }

    @ApiOperation("根据用户id查询用户名")
    @GetMapping("/getUserNameByUserId")
    public String getUserNameByUserId(String userId){
        User user = userService.getById(userId);
        return user.getUsername();
    }

    @ApiOperation("根据产品id查询产品名称")
    @GetMapping("/getProjectNameByProjectId")
    public String getProjectNameByProjectId(String projectId){
        NurseProject project = nurseProjectService.getById(projectId);
        return project.getProjectName();
    }

    @ApiOperation("获取t_nurse_project表的信息")
    @GetMapping("/getNurseProjectList")
    public List<NurseProject> getNurseProjectList(){
        return nurseProjectService.list();
    }

    @ApiOperation("添加一条服务关注信息")
    @GetMapping("/addServiceFocus")
    public ResultVo addServiceFocus(ServiceFocus serviceFocus, String elderType){
//        if (elderType!=null){
            boolean save = serviceFocusService.save(serviceFocus);
            Customer customer = new Customer();
            customer.setElderType(elderType);
            boolean update = customerService.update(customer, Wrappers.<Customer>lambdaQuery().eq(Customer::getId, serviceFocus.getCusId()));
            if (save && update){
                return ResultUtils.success("添加成功");
            }
//        }
        return ResultUtils.error("添加失败");
    }

    @ApiOperation("购买项目")
    @PostMapping("/buyProject")
    public ResultVo buyProject(ServiceFocus serviceFocus){
        boolean update = serviceFocusService.update(serviceFocus, Wrappers.<ServiceFocus>lambdaQuery().eq(ServiceFocus::getId, serviceFocus.getId()));
        if (update){
            return ResultUtils.success("购买成功");
        }
        return ResultUtils.error("购买失败");
    }

    @ApiOperation("删除一条服务关注信息")
    @DeleteMapping("/delServiceFocus")
    public ResultVo delServiceFocus(ServiceFocus serviceFocus){
        boolean b = serviceFocusService.removeById(serviceFocus.getId());
        if (b){
            return ResultUtils.success("删除成功");
        } else {
            return ResultUtils.error("删除失败");
        }
    }

    @ApiOperation("模糊查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike(Customer customer){
        List<Customer> list = customerService.list(Wrappers.<Customer>lambdaQuery().like(Customer::getCusName, customer.getCusName()));
        return ResultUtils.success("查询成功",list);
    }

}

