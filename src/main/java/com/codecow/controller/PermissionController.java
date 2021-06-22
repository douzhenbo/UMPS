package com.codecow.controller;

import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.PermissionAddReqVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.entity.SysPermission;
import com.codecow.service.IPermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
    public DataResult<List<SysPermission>> getAllMenus(){
        DataResult<List<SysPermission>> result=DataResult.success();
        result.setData(permissionService.selectAll());
        return result;
    }

    @ApiOperation("获取菜单权限树-菜单级别")
    @GetMapping("/getMenuTree")
    public DataResult<List<PermissionRespNodeVO>> getMenuTree(){
        DataResult<List<PermissionRespNodeVO>>result=DataResult.success();
        System.out.println("菜单树"+permissionService.selectAllMenuByTree());
        result.setData(permissionService.selectAllMenuByTree());
        return result;
    }

    @ApiOperation("新增菜单权限")
    @PostMapping("/addMenu")
    public DataResult addMenu(@RequestBody @Valid PermissionAddReqVO permissionAddReqVO){
        SysPermission sysPermission=permissionService.addPermission(permissionAddReqVO);
        System.out.println(sysPermission);
        return DataResult.success(sysPermission);
    }


    @ApiOperation(value = "获取到按钮级别的菜单树,用于新增角色")
    @GetMapping("/getAllMenuTree")
    public DataResult<List<PermissionRespNodeVO>> getAllMenuTree(){
        DataResult dataResult=DataResult.success();
        dataResult.setData(permissionService.selectAllTree());
        return dataResult;
    }

}
