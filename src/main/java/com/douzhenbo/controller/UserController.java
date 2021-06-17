package com.douzhenbo.controller;

import com.douzhenbo.common.utils.DataResult;
import com.douzhenbo.common.vo.req.LoginReqVO;
import com.douzhenbo.common.vo.req.UserPageVO;
import com.douzhenbo.common.vo.resp.LoginRespVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.entity.SysUser;
import com.douzhenbo.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public DataResult getUserList(@RequestBody UserPageVO vo){
        PageVO<SysUser> sysUserPageInfo=userService.pageInfo(vo);
        return DataResult.success(sysUserPageInfo);
    }

}
