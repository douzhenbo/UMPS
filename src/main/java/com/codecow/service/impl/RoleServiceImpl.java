package com.codecow.service.impl;

import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.pageutils.PageUtil;
import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.common.vo.req.RolePermissionAddReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.dao.SysRoleMapper;
import com.codecow.entity.SysRole;
import com.codecow.service.IRolePermissionService;
import com.codecow.service.IRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:41
 **/
@Service
public class RoleServiceImpl implements IRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Override
    public PageVO<SysRole> getAllRoles(RolePageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(), vo.getPageSize());
        List<SysRole>sysRoles=sysRoleMapper.selectAll(vo);
        return PageUtil.getPageVo(sysRoles);
    }


    @Override
    public List<SysRole> selectAll() {
        return sysRoleMapper.selectAll(new RolePageReqVO());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysRole addRole(AddRoleReqVO vo) {

        SysRole sysRole=new SysRole();

        BeanUtils.copyProperties(vo,sysRole);

        sysRole.setId(UUID.randomUUID().toString());

        sysRole.setCreateTime(new Date());

        int insert=sysRoleMapper.insertSelective(sysRole);

        if(insert!=1){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

       if(vo.getPermissionIds()!=null&&!vo.getPermissionIds().isEmpty()){
          RolePermissionAddReqVO rolePermissionAddReqVO=new RolePermissionAddReqVO();
          rolePermissionAddReqVO.setRoleId(sysRole.getId());
          rolePermissionAddReqVO.setPermissionIds(vo.getPermissionIds());
          rolePermissionService.addRolePermission(rolePermissionAddReqVO);
       }

        return null;
    }


}
