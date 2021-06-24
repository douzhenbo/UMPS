package com.codecow.service.impl;

import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.vo.req.UserOwnRoleReqVO;
import com.codecow.dao.SysRoleMapper;
import com.codecow.dao.SysUserRoleMapper;
import com.codecow.entity.SysUserRole;
import com.codecow.service.IUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/23 14:45
 **/
@Service
public class UserRoleImpl implements IUserRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;


    @Override
    public List<String> getRoleIdsByUserId(String userId) {
        return sysUserRoleMapper.getRoleIdsByUserId(userId);
    }

    @Override
    public void giveUserRoles(UserOwnRoleReqVO vo) {
        sysUserRoleMapper.batchRemoveRoleIdsByUserId(vo.getUserId());
        if(vo.getRoleIds()==null||vo.getRoleIds().isEmpty()){
            return;
        }
        List<SysUserRole>list=new ArrayList<>();
        for(String s:vo.getRoleIds()){
            SysUserRole sysUserRole=new SysUserRole();
            sysUserRole.setRoleId(s);
            sysUserRole.setUserId(vo.getUserId());
            sysUserRole.setCreateTime(new Date());
            sysUserRole.setId(UUID.randomUUID().toString());
            list.add(sysUserRole);
        }

        int count=sysUserRoleMapper.batchInsertRoleIdsByUserId(list);
        if (count==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
    }

    @Override
    public List<String> getUserIdsByRoleIds(List<String> roleIds) {
        return sysUserRoleMapper.getUserIdsByRoleIds(roleIds);
    }
}
