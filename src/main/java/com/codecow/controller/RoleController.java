package com.codecow.controller;

import com.codecow.aop.annotation.MyLog;
import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.common.vo.req.RoleUpdateReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.entity.SysRole;
import com.codecow.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:49
 **/
@RestController
@RequestMapping("/role")
@Api(tags = "角色管理")
public class RoleController {
    @Autowired
    private IRoleService roleService;


    @ApiOperation(value = "获取所有角色信息")
    @PostMapping("/getAllRoles")
    @MyLog(title = "角色管理",action = "获取所有角色信息")
    public DataResult<PageVO<SysRole>> getAllRoles(@RequestBody RolePageReqVO vo){
        DataResult<PageVO<SysRole>> dataResult=DataResult.success();
        dataResult.setData(roleService.getAllRoles(vo));
        System.out.println(dataResult);
        return dataResult;
    }


    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    @MyLog(title = "角色管理",action = "新增角色")
    public DataResult addRole(@RequestBody @Valid AddRoleReqVO vo){
        SysRole sysRole= roleService.addRole(vo);
        return DataResult.success(sysRole);
    }


    @ApiOperation(value = "获取角色详情接口-编辑角色时使用")
    @GetMapping("/getRoleDetailInfo/{id}")
    @MyLog(title = "角色管理",action="获取角色详情接口-编辑角色时使用")
    public DataResult getRoleDetailInfo(@PathVariable("id")String roleId){
        SysRole sysRole=roleService.detailInfo(roleId);
        return DataResult.success(sysRole);
    }

    @ApiOperation(value = "保存更新后的角色信息")
    @PutMapping("/updateRole")
    @MyLog(title = "角色管理",action = "保存更新后的角色信息")
    public DataResult updataRole(@RequestBody @Valid RoleUpdateReqVO vo){
        System.out.println(vo);
        roleService.updateRole(vo);
        return DataResult.success();
    }


    @ApiOperation(value = "删除角色信息")
    @DeleteMapping("/deleteRole/{id}")
    @MyLog(title = "角色管理",action = "删除角色信息")
    public DataResult deleteRole(@PathVariable("id")String id){
        roleService.deleteRole(id);
        return DataResult.success();
    }
}
