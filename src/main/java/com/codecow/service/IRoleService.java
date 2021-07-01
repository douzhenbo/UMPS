package com.codecow.service;

import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.common.vo.req.RoleUpdateReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.entity.SysRole;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:40
 **/
public interface IRoleService {
    /**
     * 分页查询所有角色
     * @param vo
     * @return
     */
    PageVO<SysRole>getAllRoles(RolePageReqVO vo);
    /**
     * 新增角色接口
     */
    SysRole addRole(AddRoleReqVO vo);


    /**
     * 查询所有角色（给用户赋予角色使用）
     */
    List<SysRole>selectAll();

    /**
     * 获取角色详情信息
     * @param roleId 角色id
     * @return 返回角色信息
     */
    SysRole detailInfo(String roleId);

    /**
     * 更新角色信息
     * @param vo
     */
    void updateRole(RoleUpdateReqVO vo);

    /**
     * 删除角色
     * @param roleId
     */
    void deleteRole(String roleId);

    List<String> getNamesByUserId(String userId);
}
