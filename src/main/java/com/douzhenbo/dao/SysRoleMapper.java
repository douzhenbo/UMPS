package com.douzhenbo.dao;

import com.douzhenbo.common.vo.req.AddRoleReqVO;
import com.douzhenbo.common.vo.req.RolePageReqVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.entity.SysRole;

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
