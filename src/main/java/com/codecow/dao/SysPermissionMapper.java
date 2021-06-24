package com.codecow.dao;

import com.codecow.entity.SysPermission;

import java.util.List;

public interface SysPermissionMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysPermission record);

    int insertSelective(SysPermission record);

    SysPermission selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysPermission record);

    int updateByPrimaryKey(SysPermission record);


    /**
     * 查询所有的菜单权限
     * @return
     */
    List<SysPermission>selectAll();

    /**
     * 查找子集菜单
     */
    List<String>selectChild(String parentId);
}
