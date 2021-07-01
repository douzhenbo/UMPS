package com.codecow.controller;

import com.codecow.aop.annotation.MyLog;
import com.codecow.common.constants.Constant;
import com.codecow.common.utils.DataResult;
import com.codecow.common.utils.jwtutils.JwtTokenUtil;
import com.codecow.common.vo.req.*;
import com.codecow.common.vo.resp.LoginRespVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.common.vo.resp.UserOwnRoleRespVO;
import com.codecow.entity.SysUser;
import com.codecow.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:27
 **/


@RestController
@Api(tags = "组织模块-用户管理")
@Slf4j
public class UserController {
    @Autowired
    private IUserService userService;


    @PostMapping("/login")
    @ApiOperation(value = "用户登录接口")
    @MyLog(title = "用户管理",action = "用户登录")
    public DataResult<LoginRespVO> login(@RequestBody LoginReqVO vo){
        DataResult<LoginRespVO> result= DataResult.success();
        result.setData(userService.login(vo));
        return result;
    }


    @PostMapping("/user/getUserList")
    @ApiOperation(value = "获取用户列表")
    @MyLog(title = "用户管理",action = "获取用户列表")
    @RequiresPermissions("sys:user:list")
    public DataResult getUserList(@RequestBody UserPageReqVO vo){
        PageVO<SysUser> sysUserPageInfo=userService.pageInfo(vo);
        return DataResult.success(sysUserPageInfo);
    }

    @PostMapping("/user/addUser")
    @ApiOperation(value = "添加用户接口")
    @MyLog(title = "用户管理",action = "添加用户")
    @RequiresPermissions("sys:user:add")
    public DataResult addUser(@RequestBody @Valid AddUserReqVO vo){
        userService.addUser(vo);
        return DataResult.success();
    }

    @GetMapping("/user/getUserRoles/{userId}")
    @ApiOperation(value = "获取用户所拥有的角色和所有角色")
    @MyLog(title = "用户管理",action = "获取用户所拥有的角色和所有角色")
    public DataResult<UserOwnRoleRespVO>getUserRoles(@PathVariable("userId") String userId){
        DataResult result=DataResult.success();
        result.setData(userService.getUserOwnRole(userId));
        return result;
    }


    @PutMapping("/user/giveRoles")
    @ApiOperation(value = "赋予用户角色")
    @MyLog(title = "用户管理",action = "赋予用户角色")
    @RequiresPermissions("sys:user:role:update")
    public DataResult giveRoles(@RequestBody @Valid UserOwnRoleReqVO vo){
       userService.setUserOwnRole(vo);
       return DataResult.success();
    }


    @ApiOperation(value = "自动刷新token，还没有更新文档")
    @GetMapping("/user/refreshToken")
    @MyLog(title = "用户管理",action = "自动刷新token")
    public DataResult refreshToken(HttpServletRequest servletRequest){
        String refreshToken=servletRequest.getHeader(Constant.JWT_REFRESH_KEY);
        return DataResult.success(userService.refreshToken(refreshToken));
    }

    @ApiOperation(value = "列表更新用户信息接口")
    @PutMapping("/user/updateUser")
    @MyLog(title = "用户管理",action = "列表更新用户信息")
    @RequiresPermissions("sys:user:update")
    public DataResult updateUserInfo(@RequestBody @Valid UserUpdateReqVO vo,HttpServletRequest request){
        userService.updateUserInfo(vo, JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN)));
        DataResult result=DataResult.success();
        return result;
    }

    @ApiOperation(value = "删除&批量删除用户接口")
    @DeleteMapping ("/user/deleteUsers")
    @MyLog(title = "用户管理",action = "删除&批量删除用户")
    @RequiresPermissions("sys:user:delete")
    public DataResult deleteUsers(@RequestBody @ApiParam("用户id集合")List<String>list,HttpServletRequest request){
        userService.deleteUsers(list,JwtTokenUtil.getUserId(request.getHeader(Constant.ACCESS_TOKEN)));
        return DataResult.success();
    }

    @ApiOperation(value = "用户退出登录")
    @GetMapping("/user/logout")
    @MyLog(title = "用户管理",action = "用户退出登录")
    public DataResult logout(HttpServletRequest request){
        try {
            String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
            String refreshToken=request.getHeader(Constant.JWT_REFRESH_KEY);
            userService.logout(accessToken,refreshToken);
        }catch (Exception e){
            log.error("logout error{}",e);
        }

        return DataResult.success();
    }

    @ApiOperation(value = "用户信息详情接口-用户编辑个人信息时使用")
    @GetMapping("/user/detailUserInfo")
    @MyLog(title = "用户管理",action = "获取用户个人详细信息-用户编辑使用")
    public DataResult<SysUser>detailUserInfo(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId=JwtTokenUtil.getUserId(accessToken);
        DataResult<SysUser>result=new DataResult<>();
        result.setData(userService.detailUserInfo(userId));
        return result;
    }


    @PutMapping("/user/saveUserInfo")
    @ApiOperation(value = "保存个人信息接口")
    @MyLog(title = "组织管理-用户管理",action = "保存个人信息接口")
    public DataResult saveUserInfo(@RequestBody UserUpdateDetailReqVO vo,HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String id=JwtTokenUtil.getUserId(accessToken);
        userService.userUpdateDetailInfo(vo,id);
        DataResult result=DataResult.success();
        return result;
    }


    @PutMapping("/user/updatePassword")
    @ApiOperation(value = "修改密码接口")
    @MyLog(title = "用户管理",action = "更新密码")
    public DataResult updatePwd(@RequestBody @Valid UserUpdatePwdReqVO vo,HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String refreshgToken=request.getHeader(Constant.JWT_REFRESH_KEY);
        try {
            userService.userUpdatePwd(vo,accessToken,refreshgToken);
        }catch (Exception e){
            log.info("e:{}",e);
        }
        DataResult result=DataResult.success();
        return result;
    }

}
