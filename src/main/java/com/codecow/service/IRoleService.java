package com.codecow.service;

import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.entity.SysRole;

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
