package com.douzhenbo.service;

import com.douzhenbo.common.vo.req.LoginReqVO;
import com.douzhenbo.common.vo.req.UserPageVO;
import com.douzhenbo.common.vo.resp.LoginRespVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:14
 **/
public interface IUserService {
    LoginRespVO login(LoginReqVO vo);
    PageVO<SysUser> pageInfo(UserPageVO vo);
}
