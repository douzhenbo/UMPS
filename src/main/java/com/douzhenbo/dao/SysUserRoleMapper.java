package com.douzhenbo.dao;

import com.douzhenbo.entity.SysUserRole;

public interface SysUserRoleMapper {
    int deleteByPrimaryKey(Long roleId);

    int insert(SysUserRole record);

    int insertSelective(SysUserRole record);

    SysUserRole selectByPrimaryKey(Long roleId);

    int updateByPrimaryKeySelective(SysUserRole record);

    int updateByPrimaryKey(SysUserRole record);
}