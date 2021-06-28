package com.codecow.service;

import com.codecow.common.vo.req.RolePermissionAddReqVO;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 16:47
 **/
public interface IRolePermissionService {
    void addRolePermission(RolePermissionAddReqVO vo);
    List<String>getRoleIdsByPermissionId(String PermissionId);

    int removeByPermissionId(String permissionId);

    List<String>getPermissionIdsByRoleId(String roleId);
}
