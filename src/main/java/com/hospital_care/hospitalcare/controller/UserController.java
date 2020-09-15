package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.role.Role;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
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
        return ResultUtils.success("成功", userList);
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
    public ResultVo updateSaveUser(User user){
        System.out.println("updateSaveUser: "+user.toString());
        //判断用户是否存在
        User user1 = userService.getUserByUserName(user.getUsername());
        if (user1 != null){
            if (user1.getId()!=user.getId()){
                return ResultUtils.error("用户名已经存在!");
            }
        }
        boolean b = userService.updateById(user);
        if(b){
            return ResultUtils.success("编辑成功");
        }else{
            return ResultUtils.error("编辑失败");
        }
    }

    /**
     * 根据用户id删除
     * @return
     */
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
        LambdaQueryWrapper<User> lambdaQuery = Wrappers.lambdaQuery();
        lambdaQuery.like(User::getUsername, user.getUsername()).like(User::getPhone, user.getPhone());
        List<User> list = userService.list(lambdaQuery);
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

