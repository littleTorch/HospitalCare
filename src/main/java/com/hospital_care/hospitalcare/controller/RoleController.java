package com.hospital_care.hospitalcare.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hospital_care.hospitalcare.entity.User;
import com.hospital_care.hospitalcare.entity.UserRole;
import com.hospital_care.hospitalcare.entity.permission.Permission;
import com.hospital_care.hospitalcare.entity.permission.vo.TreeVo;
import com.hospital_care.hospitalcare.entity.role.Role;
import com.hospital_care.hospitalcare.entity.role.vo.ParmVo;
import com.hospital_care.hospitalcare.entity.role.vo.PerVo;
import com.hospital_care.hospitalcare.entity.role_permission.vo.PermissionRoleParmVo;
import com.hospital_care.hospitalcare.result.ResultUtils;
import com.hospital_care.hospitalcare.result.ResultVo;
import com.hospital_care.hospitalcare.service.PermissionService;
import com.hospital_care.hospitalcare.service.RolePermissionService;
import com.hospital_care.hospitalcare.service.RoleService;
import com.hospital_care.hospitalcare.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author hostpital_care
 * @since 2020-09-10
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RolePermissionService rolePermissionService;
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("分配权限树查询")
    @GetMapping("/permissionTree")
    public ResultVo permissionTree(PerVo perVo){
        List<Permission> permissions = null;
        //1.查询当前用户的所有权限
        User user = userService.getById(perVo.getUserId());
        if(user.getId().equals("1")){
            permissions = permissionService.list();
        }else {
            permissions = permissionService.getPermissionByUserId(perVo.getUserId());
        }
        //查询角色原来的数据，设置为选中
        List<Permission> permissionListByRoleId = permissionService.getPermissionListByRoleId(perVo.getRoleId());
        //组装成树
        List<TreeVo> listTree = new ArrayList<>();
        if (permissions != null){
            for(int i = 0; i < permissions.size(); i++){
                TreeVo tree = new TreeVo();
                tree.setId(permissions.get(i).getId());
                tree.setPid(permissions.get(i).getParentId());
                tree.setName(permissions.get(i).getLabel());
                if(permissionListByRoleId.size() > 0){
                    for(int j = 0; j < permissionListByRoleId.size(); j++){
                        if(permissions.get(i).getId().equals(permissionListByRoleId.get(j).getId())) {
                            tree.setChecked(true);
                            break;
                        }
                    }
                }
                listTree.add(tree);
            }

        }
        return ResultUtils.success("成功",listTree);
    }

    @ApiOperation("保存权限")
    @PostMapping("/saveAssignRole")
    public ResultVo saveAssignRole(@RequestBody PermissionRoleParmVo parmVo){
        if(parmVo != null && !parmVo.getList().isEmpty()){
            List<TreeVo> list = parmVo.getList();
            List<Integer> ids = list.stream().filter(item -> item != null).map(item -> item.getId()).collect(Collectors.toList());
            rolePermissionService.saveAssignRole(parmVo.getRoleId(),ids);

            return ResultUtils.success("分配成功!");
        }else{
            return ResultUtils.error("请选择权限!");
        }
    }

    @ApiOperation(value = "新增角色")
    @RequestMapping(value = "addRole",method = RequestMethod.POST)
    public ResultVo addRole(Role role){
        boolean b = roleService.save(role);
        if(b){
            return ResultUtils.success("新增成功!");
        }else{
            return ResultUtils.error("新增失败！");
        }
    }

    @ApiOperation(value = "根据名称查询角色")
    @GetMapping("/selectByLike")
    public ResultVo getRoleByName(String name){
        List<Role> roleList = roleService.query().like("name", name).list();
        if (roleList.size()>0){
            return ResultUtils.success("成功",roleList);
        } else {
            return ResultUtils.success("失败",roleList);
        }
    }

    @ApiOperation(value = "编辑角色")
    @RequestMapping(value = "/updateRole",method = RequestMethod.POST)
    public ResultVo updateRole(Role role){
        boolean b = roleService.updateById(role);
        if(b){
            return ResultUtils.success("编辑角色成功!");
        }else{
            return ResultUtils.error("编辑角色失败!");
        }
    }

    @ApiOperation(value = "删除角色")
    @GetMapping("/deleteOne")
    public ResultVo deleteRole(String id){
        boolean b = roleService.removeById(id);
        if(b){
            return ResultUtils.success("删除角色成功!");
        }else{
            return ResultUtils.error("删除角色失败!");
        }
    }

    @ApiOperation(value = "查询角色列表")
    @GetMapping("/getRoleList")
    public ResultVo getRoleList(ParmVo parmVo){
        IPage<Role> page = new Page();
        page.setSize(parmVo.getPageSize());
        page.setCurrent(parmVo.getCurrentPage());
        IPage<Role> roleList = roleService.page(page);
        return ResultUtils.success("查询成功",roleList);
    }


    @ApiOperation("分配角色角色列表")
    @PostMapping("/getRoleListForAssing")
    public ResultVo getRoleListForAssing(){
        List<Role> list = roleService.list();
        return ResultUtils.success("查询成功",list);
    }

    @ApiOperation("根据用户id查询角色id")
    @GetMapping("/getRoleIdByUserId")
    public ResultVo getRoleIdByUserId(Integer userId){
        //UserRole userRole1 = roleService.getRoleIdByUserId(userRole.getUserId());
        UserRole userRole1 = roleService.getRoleIdByUserId(userId);
        return ResultUtils.success("成功",userRole1);
    }

    @ApiOperation("分配用户角色")
    @PostMapping("/assignRole")
    public ResultVo assignRole(@RequestBody UserRole userRole){
        roleService.assignRole(userRole);
        return ResultUtils.success("分配角色成功!");
    }

}

