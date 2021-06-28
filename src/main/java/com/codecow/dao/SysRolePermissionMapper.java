package com.codecow.dao;

import com.codecow.entity.SysRolePermission;

import java.util.List;

public interface SysRolePermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRolePermission record);

    int insertSelective(SysRolePermission record);

    SysRolePermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRolePermission record);

    int updateByPrimaryKey(SysRolePermission record);

    int batchInsertRolePermission(List<SysRolePermission>list);

    List<String>getRoleIdsByPermissionId(String permissionId);

    int removeByPermissionId(String permissionId);

    /**
     * 根据roldId获取与角色相关的菜单id(编辑角色时使用)
     * @param roleId 角色id
     * @return 返回与该角色相关的菜单id
     */
    List<String>getPermissionIdsByRoleId(String roleId);

    int removeByRoleId(String roleId);

}
