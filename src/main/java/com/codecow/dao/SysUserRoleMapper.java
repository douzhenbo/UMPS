package com.codecow.dao;

import com.codecow.entity.SysUserRole;

import java.util.List;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);

    /**
     * 通过用户Id查询所有的用户所拥有的所有角色id
     * @param userId
     * @return
     */
    List<String>getRoleIdsByUserId(String userId);

    /**
     * 批量删除用户角色
     * @param userId
     * @return
     */
    int batchRemoveRoleIdsByUserId(String userId);

    /**
     * 批量增加用户角色
     * @param list
     * @return
     */
    int batchInsertRoleIdsByUserId(List<SysUserRole>list);

    /**
     * 通过roleIds获取用户ids
     * @param roleIds
     * @return
     */
    List<String>getUserIdsByRoleIds(List<String>roleIds);

    int removeUserRoleId(String roleId);
}
