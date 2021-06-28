package com.codecow.controller;

import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
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
    public DataResult<PageVO<SysRole>> getAllRoles(@RequestBody RolePageReqVO vo){
        DataResult<PageVO<SysRole>> dataResult=DataResult.success();
        dataResult.setData(roleService.getAllRoles(vo));
        System.out.println(dataResult);
        return dataResult;
    }


    @ApiOperation(value = "新增角色")
    @PostMapping("/addRole")
    public DataResult addRole(@RequestBody @Valid AddRoleReqVO vo){
        SysRole sysRole= roleService.addRole(vo);
        return DataResult.success(sysRole);
    }


    @ApiModelProperty(value = "获取角色详情接口-编辑角色时使用")
    @GetMapping("/getRoleDetailInfo/{id}")
    public DataResult getRoleDetailInfo(@PathVariable("id")String roleId){
        SysRole sysRole=roleService.detailInfo(roleId);
        return DataResult.success(sysRole);
    }

}
