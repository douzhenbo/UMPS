package com.codecow.dao;

import com.codecow.common.vo.req.UserPageReqVO;
import com.codecow.entity.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUsername(String username);

    SysUser getUserByName(String username);

    List<SysUser> selectAll(UserPageReqVO vo);
}

