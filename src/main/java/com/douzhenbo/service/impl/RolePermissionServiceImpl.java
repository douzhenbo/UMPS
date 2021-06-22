package com.douzhenbo.service.impl;

import com.douzhenbo.common.exceptions.BusinessException;
import com.douzhenbo.common.exceptions.code.BaseResponseCode;
import com.douzhenbo.common.vo.req.RolePermissionAddReqVO;
import com.douzhenbo.dao.SysRolePermissionMapper;
import com.douzhenbo.entity.SysRolePermission;
import com.douzhenbo.service.IRolePermissionService;
import org.springframework.beans.BeanUtils;
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
}
