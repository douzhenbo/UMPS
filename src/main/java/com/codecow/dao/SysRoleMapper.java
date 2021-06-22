package com.codecow.dao;

import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.entity.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(String id);

    int insert(SysRole record);

    int insertSelective(SysRole record);

    SysRole selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(SysRole record);

    int updateByPrimaryKey(SysRole record);

    /**
     * 查询所有角色信息
     * @param vo
     * @return
     */
    List<SysRole> selectAll(RolePageReqVO vo);


}
