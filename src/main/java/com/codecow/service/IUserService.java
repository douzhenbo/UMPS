package com.codecow.service;

import com.codecow.common.vo.req.*;
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

    /**
     * 自动刷新token
     * @param refreshToken
     * @return
     */
    String refreshToken(String refreshToken);


    /**
     * 更新用户信息
     */
    void updateUserInfo(UserUpdateReqVO vo,String operationId);


    /**
     * 删除用户&批量删除（逻辑删除）
     */
    void deleteUsers(List<String>list,String operationId);
}
