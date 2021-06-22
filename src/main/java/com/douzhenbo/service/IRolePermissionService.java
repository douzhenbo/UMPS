package com.douzhenbo.service;

import com.douzhenbo.common.vo.req.RolePermissionAddReqVO;
import com.douzhenbo.entity.SysRolePermission;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 16:47
 **/
public interface IRolePermissionService {
    void addRolePermission(RolePermissionAddReqVO vo);
}
