package com.codecow.controller;

import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.AddUserReqVO;
import com.codecow.common.vo.req.LoginReqVO;
import com.codecow.common.vo.req.UserOwnRoleReqVO;
import com.codecow.common.vo.req.UserPageReqVO;
import com.codecow.common.vo.resp.LoginRespVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.common.vo.resp.UserOwnRoleRespVO;
import com.codecow.entity.SysUser;
import com.codecow.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:27
 **/


@RestController
@Api(tags = "组织模块-用户管理")
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    public DataResult<LoginRespVO> login(@RequestBody LoginReqVO vo){
        DataResult<LoginRespVO> result= DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }


    @PostMapping("/user/getUserList")
    @ApiOperation(value = "获取用户列表")
    @RequiresPermissions("sys:user:list")
    public DataResult getUserList(@RequestBody UserPageReqVO vo){
        PageVO<SysUser> sysUserPageInfo=userService.pageInfo(vo);
        return DataResult.success(sysUserPageInfo);
    }

    @PostMapping("/user/addUser")
    @ApiOperation(value = "添加用户接口")
    public DataResult addUser(@RequestBody @Valid AddUserReqVO vo){
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/getUserRoles/{userId}")
    @ApiOperation(value = "获取用户所拥有的角色和所有角色")
    public DataResult<UserOwnRoleRespVO>getUserRoles(@PathVariable("userId") String userId){
        DataResult result=DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }


    @PutMapping("/user/giveRoles")
    @ApiOperation(value = "赋予用户角色")
    public DataResult giveRoles(@RequestBody @Valid UserOwnRoleReqVO vo){
       userService.setUserOwnRole(vo);
       return DataResult.success();
    }
}
