package com.hospital_care.hospitalcare.controller;

import com.alibaba.druid.sql.dialect.mysql.ast.statement.MysqlDeallocatePrepareStatement;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hospital_care.hospitalcare.entity.Emp;
import com.hospital_care.hospitalcare.entity.MyMsg;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.service.EmpService;
import com.hospital_care.hospitalcare.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @Author: zzj
 * @Date: 2020-08-12 21:00
 * @Version 1.0
 */
@Api(tags = "个人中心页面 API")
@RestController
@RequestMapping("/my")
public class MyController {

    @Autowired
    private UserService userService;

    @Autowired
    private EmpService empService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @ApiOperation("获取个人信息")
    @GetMapping
    public MyMsg index(String userId){
        System.out.println("----------------"+userId);
        User user = userService.getById(userId);
        Emp emp=empService.getById(user.getEmpId());
        MyMsg myMsg = new MyMsg();
        myMsg.setEmp(emp);
        myMsg.setUser(user);
        return myMsg;
    }

    @ApiOperation("获取头像")
    @GetMapping("/icon")
    public void ShowImg(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userId = request.getParameter("userId");
        User user = userService.getById(userId);
        //这里是存放图片的文件夹地址
        String path= System.getProperty("user.dir")+"\\userIcon";
        response.setContentType("image/jpeg");
        File file=null;
        try {
            file = new File(path+"\\"+user.getUserIcon());
            BufferedImage image= ImageIO.read(file);
            //得到向客户端输出二进制数据的对象
            ServletOutputStream out = response.getOutputStream();
            ImageIO.write(image, "png", out);
            if (out != null) {
                out.close();
            }
        } catch (Exception e) {
            System.out.println("系统找不到图像文件："+path+"\\"+user.getUserIcon());
            return;
        }
    }


    @ApiOperation("图片上传")
    @PostMapping("/upload")
    public String upload(@RequestParam("avatarfile") MultipartFile file, HttpServletRequest request) throws IOException {
        String userId = request.getParameter("userId");
        // 上传路径保存设置
        String path = System.getProperty("user.dir")+"\\userIcon";
        File realPath = new File(path);
        if (!realPath.exists()){
            realPath.mkdir();
        }
        //文件上传地址
        System.out.println("上传文件保留地址："+realPath);
        file.transferTo(new File(realPath+"/"+userId+".png"));
        User user = userService.getById(userId);
        user.setUserIcon(userId+".png");
        userService.updateById(user);
        return "ok";
    }

    @ApiOperation("更新个人信息")
    @PutMapping("/setMyMsg")
    public String setMyMsg( String userId,  String empName, String phone, Integer sex,String brief){
        Emp emp = empService.getById(userService.getById(userId).getEmpId());
        emp.setEmpName(empName);
        emp.setPhone(phone);
        emp.setSex(sex);
        emp.setBrief(brief);
        empService.updateById(emp);
        return "ok";
    }

    @ApiOperation("改密码")
    @PutMapping("/setPas")
    public String setPas( String userId, String newPassword, String oldPassword){
        User user = userService.getById(userId);
        if (!passwordEncoder.matches(oldPassword,user.getPassword())){
            return "旧密码不正确！";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        if (userService.updateById(user)){
            return "修改成功";
        }
        return "修改失败";
    }
}