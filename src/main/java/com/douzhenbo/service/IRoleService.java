package com.douzhenbo.service;

import com.douzhenbo.common.vo.req.AddRoleReqVO;
import com.douzhenbo.common.vo.req.RolePageReqVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.entity.SysRole;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:40
 **/
public interface IRoleService {
    PageVO<SysRole>getAllRoles(RolePageReqVO vo);
    /**
     * 新增角色接口
     */
    SysRole addRole(AddRoleReqVO vo);
}
