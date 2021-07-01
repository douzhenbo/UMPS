package com.codecow.service;

import com.codecow.common.vo.req.PermissionAddReqVO;
import com.codecow.common.vo.req.PermissionUpdateReqVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.entity.SysPermission;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 10:50
 **/

public interface IPermissionService {
    /**
     * @author codecow
     * 获取所有权限
     * @return  返回所有的权限
     */
    List<SysPermission> selectAll();

    /**
     * @author codecow
     * 获取权限树，到菜单层级
     */
    List<PermissionRespNodeVO>selectAllMenuByTree();

    /**
     * @author codecow
     * 新添加权限
     */
    SysPermission addPermission(PermissionAddReqVO permissionAddReqVO);


    /**
     * @author codecow
     * 获取用户菜单
     */
    List<PermissionRespNodeVO> permissionTreeList(String userId);


    /**
     * @author codecow
     * @return
     */
    List<PermissionRespNodeVO>selectAllTree();

    /**
     * @author codecow
     */
    void updatePermission(PermissionUpdateReqVO vo);

    /**
     * 删除权限接口
     */
    void deletePermission(String permissionId);


    /**
     * 获取用户权限
     */

    List<String> getPermissionByUserId(String userId);

    List<SysPermission> getPermissions(String userId);
}
