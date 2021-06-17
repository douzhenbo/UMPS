package com.douzhenbo.dao;

import com.douzhenbo.entity.SysDept;

public interface SysDeptMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysDept record);

    int insertSelective(SysDept record);

    SysDept selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysDept record);

    int updateByPrimaryKey(SysDept record);
}