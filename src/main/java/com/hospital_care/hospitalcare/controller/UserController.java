package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.Emp;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.role.Role;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.entity.user.vo.UserVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.EmpService;
import com.hospital_care.hospitalcare.service.RoleService;
import com.hospital_care.hospitalcare.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@Api(tags = "user API")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EmpService empService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("后端分页获取-user-表信息")
    @GetMapping("/getUserList")
    public ResultVo index(ParmVo parm){
        IPage<User> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<User> userList = userService.page(page);
        List<Emp> empList = empService.list();
        List<User> records = userList.getRecords();
        for (User user : records) {
            for (Emp emp : empList) {
                if (emp.getId().equals(user.getEmpId())){
                    user.setEmpName(emp.getEmpName());
                }
            }
        }
        return ResultUtils.success("成功", userList);
    }

    @ApiOperation("获取员工表信息")
    @GetMapping("/getEmpList")
    public List<Emp> getEmpList(){
        return empService.list();
    }


    @ApiOperation("新增用户")
    @PostMapping("/addUser")
    public ResultVo addUser(User user){
        QueryWrapper<User> query = new QueryWrapper<>();
        query.lambda().eq(User::getUsername,user.getUsername());
        //查询用户是否存在
        User one = userService.getOne(query);
        if(one != null){
            return ResultUtils.error("用户名已经存在!");
        }
        //加密用户密码
        String pwd = passwordEncoder.encode(user.getPassword());
        user.setPassword(pwd);
        boolean b = userService.save(user);
        if(b){
            return ResultUtils.success("新增用户成功");
        }else{
            return ResultUtils.error("新增用户失败");
        }
    }


    @ApiOperation("编辑用户保存")
    @PostMapping("/updateSaveUser")
    public ResultVo updateSaveUser(UserVo userVo){
        System.out.println("updateSaveUser: "+userVo.toString());
        //判断用户是否存在
        User temp = userService.getUserByUserName(userVo.getUsername());
        if (temp != null){
            if (temp.getId()!=userVo.getId()){
                return ResultUtils.error("用户名已经存在!");
            }
        }
        User user = new User();
        user.setId(userVo.getId());
        user.setUpdateBy(userVo.getUpdateBy());
        user.setUsername(userVo.getUsername());
        //user.setPassword(userVo.getPassword());
        user.setSex(userVo.getSex());
        user.setEmail(userVo.getEmail());
        user.setPhone(userVo.getPhone());
        user.setEmpId(userVo.getEmpId());
        boolean b = userService.updateById(user);
        if(b){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    @ApiOperation("根据用户id删除")
    @GetMapping("/deleteUserById")
    public ResultVo deleteUserById(String userId){
        boolean b = userService.removeById(userId);
        if(b){
            return ResultUtils.success("删除用户成功");
        }else{
            return ResultUtils.error("删除用户失败");
        }
    }

    @ApiOperation("模糊查询")
    @GetMapping("/selectByLike")
    public ResultVo selectByLike(User user){
        //LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        //lambdaQuery.like(User::getUsername, user.getUsername()).like(User::getPhone, user.getPhone());
        QueryWrapper<User> query = Wrappers.query();
        query.like("username", "%"+user.getUsername()+"%").like("phone", "%"+user.getPhone()+"%");
        List<User> list = userService.list(query);
        return ResultUtils.success("查询成功",list);
    }

    @ApiOperation("分配角色角色列表")
    @PostMapping("/getRoleListForAssing")
    public ResultVo getRoleListForAssing(){
        List<Role> list = roleService.list();
        return ResultUtils.success("查询成功", list);
    }

    /**
     * 获取用户信息列表
     * @return
     */
    @RequestMapping("/getList")
    public ResultVo getList(){
        List<User> list = userService.list();
        return ResultUtils.success("成功",list);
    }

}

