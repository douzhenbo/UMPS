package com.codecow.controller;

import com.codecow.aop.annotation.MyLog;
import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.PermissionAddReqVO;
import com.codecow.common.vo.req.PermissionUpdateReqVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.entity.SysPermission;
import com.codecow.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 10:56
 **/

@RestController
@RequestMapping("/menu")
@Api(tags = "菜单管理接口")
public class PermissionController {
    @Autowired
    private IPermissionService permissionService;

    @ApiOperation("获取所有的菜单权限数据")
    @GetMapping("/getAllMenus")
    @MyLog(title = "菜单管理",action = "获取所有菜单权限数据")
    @RequiresPermissions("sys:permission:list")
    public DataResult<List<SysPermission>> getAllMenus(){
        DataResult<List<SysPermission>> result=DataResult.success();
        result.setData(permissionService.selectAll());
        return result;
    }

    @ApiOperation("获取菜单权限树-菜单级别")
    @GetMapping("/getMenuTree")
    @MyLog(title = "菜单管理",action = "获取菜单权限树-菜单级别")
    @RequiresPermissions(value = {"sys:permission:update","sys:permission:add"},logical = Logical.OR)
    public DataResult<List<PermissionRespNodeVO>> getMenuTree(){
        DataResult<List<PermissionRespNodeVO>>result=DataResult.success();
        result.setData(permissionService.selectAllMenuByTree());
        return result;
    }

    @ApiOperation("新增菜单权限")
    @PostMapping("/addMenu")
    @MyLog(title="菜单管理",action = "新增菜单权限")
    @RequiresPermissions("sys:permission:add")
    public DataResult addMenu(@RequestBody @Valid PermissionAddReqVO permissionAddReqVO){
        SysPermission sysPermission=permissionService.addPermission(permissionAddReqVO);
        return DataResult.success(sysPermission);
    }


    @ApiOperation(value = "获取到按钮级别的菜单树,用于新增角色")
    @GetMapping("/getAllMenuTree")
    @MyLog(title = "菜单管理",action ="获取到按钮级别的菜单树,用于新增角色")
    @RequiresPermissions(value = {"sys:role:update","sys:role:add"},logical = Logical.OR)
    public DataResult<List<PermissionRespNodeVO>> getAllMenuTree(){
        DataResult dataResult=DataResult.success();
        dataResult.setData(permissionService.selectAllTree());
        return dataResult;
    }


    @ApiOperation(value = "编辑菜单接口")
    @PutMapping("/updateMenu")
    @MyLog(title = "菜单管理",action = "编辑菜单")
    @RequiresPermissions("sys:permission:update")
    public DataResult updateMenu(@RequestBody@Valid PermissionUpdateReqVO vo){
        permissionService.updatePermission(vo);
        DataResult dataResult=DataResult.success();
        return dataResult;
    }


    @DeleteMapping("/deleteMenu/{permissionId}")
    @ApiOperation(value = "删除菜单")
    @MyLog(title = "菜单管理",action = "删除菜单")
    @RequiresPermissions("sys:permission:delete")
    public DataResult deleteMenu(@PathVariable("permissionId")String permissionId){
        permissionService.deletePermission(permissionId);
        return DataResult.success();
    }

}
