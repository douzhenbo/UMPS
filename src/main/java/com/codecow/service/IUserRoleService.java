package com.codecow.service;

import com.codecow.common.vo.req.UserOwnRoleReqVO;
import com.codecow.entity.SysUser;
import com.codecow.entity.SysUserRole;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/23 14:45
 **/
public interface IUserRoleService {
    List<String> getRoleIdsByUserId(String userId);

    void giveUserRoles(UserOwnRoleReqVO vo);
}
