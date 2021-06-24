package com.codecow.service.impl;

import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.vo.req.RolePermissionAddReqVO;
import com.codecow.dao.SysRolePermissionMapper;
import com.codecow.entity.SysRolePermission;
import com.codecow.service.IRolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 16:48
 **/
@Service
public class RolePermissionServiceImpl implements IRolePermissionService {
    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Override
    public void addRolePermission(RolePermissionAddReqVO vo) {
        if(vo.getPermissionIds()==null||vo.getPermissionIds().isEmpty()){
            return;
        }
        Date CreateTime=new Date();
        List<SysRolePermission>list=new ArrayList<>();
        for(String permissionId:vo.getPermissionIds()){
            SysRolePermission sysRolePermission=new SysRolePermission();
            sysRolePermission.setId(UUID.randomUUID().toString());
            sysRolePermission.setCreateTime(CreateTime);
            sysRolePermission.setRoleId(vo.getRoleId());
            sysRolePermission.setPermissionId(permissionId);
            list.add(sysRolePermission);
        }
        int insert=sysRolePermissionMapper.batchInsertRolePermission(list);
        if(insert==0){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
    }


    /**
     * 通过菜单id过去RoleIds
     */
    @Override
    public List<String> getRoleIdsByPermissionId(String permissionId) {
        return sysRolePermissionMapper.getRoleIdsByPermissionId(permissionId);
    }
}
