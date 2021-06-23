package com.codecow.service;

import com.codecow.common.vo.req.AddUserReqVO;
import com.codecow.common.vo.req.LoginReqVO;
import com.codecow.common.vo.req.UserOwnRoleReqVO;
import com.codecow.common.vo.req.UserPageReqVO;
import com.codecow.common.vo.resp.LoginRespVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.common.vo.resp.UserOwnRoleRespVO;
import com.codecow.entity.SysUser;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:14
 **/
public interface IUserService {
    LoginRespVO login(LoginReqVO vo);
    PageVO<SysUser> pageInfo(UserPageReqVO vo);
    SysUser addUser(AddUserReqVO vo);

    UserOwnRoleRespVO getUserOwnRole(String userId);

    void setUserOwnRole(UserOwnRoleReqVO vo);
}
