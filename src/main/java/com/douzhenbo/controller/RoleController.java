package com.douzhenbo.controller;

import com.douzhenbo.common.utils.DataResult;
import com.douzhenbo.common.vo.req.AddRoleReqVO;
import com.douzhenbo.common.vo.req.RolePageReqVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.entity.SysRole;
import com.douzhenbo.service.IRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public DataResult<PageVO<SysRole>> getAllRoles(@RequestBody RolePageReqVO vo){
        DataResult<PageVO<SysRole>> dataResult=DataResult.success();
        dataResult.setData(roleService.getAllRoles(vo));
        System.out.println(dataResult);
        return dataResult;
    }


    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    DataResult addRole(@RequestBody @Valid AddRoleReqVO vo){
        SysRole sysRole= roleService.addRole(vo);
        return DataResult.success(sysRole);
    }
}
