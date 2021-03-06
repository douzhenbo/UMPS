package com.codecow.service.impl;

import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.jwtutils.TokenSetting;
import com.codecow.common.utils.pageutils.PageUtil;
import com.codecow.common.vo.req.AddRoleReqVO;
import com.codecow.common.vo.req.RolePageReqVO;
import com.codecow.common.vo.req.RolePermissionAddReqVO;
import com.codecow.common.vo.req.RoleUpdateReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.dao.SysRoleMapper;
import com.codecow.dao.SysUserRoleMapper;
import com.codecow.entity.SysRole;
import com.codecow.service.*;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:41
 **/
@Service
@Slf4j
public class RoleServiceImpl implements IRoleService {
    @Autowired
    SysRoleMapper sysRoleMapper;

    @Autowired
    IRolePermissionService rolePermissionService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    RedisService redisService;
    @Autowired
    IUserRoleService userRoleService;

    @Autowired
    TokenSetting tokenSetting;
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


    @Override
    public SysRole detailInfo(String roleId) {
        SysRole role=sysRoleMapper.selectByPrimaryKey(roleId);

        if(role==null){
            log.error("?????????id:{}?????????",roleId);
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        //???????????????????????????
        List<PermissionRespNodeVO>permissionRespNodeVOS=permissionService.selectAllTree();

        //????????????????????????????????????
        List<String>permissionIds=rolePermissionService.getPermissionIdsByRoleId(roleId);
        Set<String>checkList=new HashSet<>(permissionIds);
        setChecked(permissionRespNodeVOS,checkList);
        role.setPermissions(permissionRespNodeVOS);

        return role;

    }


    public void setChecked(List<PermissionRespNodeVO>list,Set<String>checkList){
        /**
         * ?????????????????????????????????????????????????????????????????????????????????
         */
        for(PermissionRespNodeVO nodeVO:list){

            if(checkList.contains(nodeVO.getId())&&(nodeVO.getChildren()==null||nodeVO.getChildren().isEmpty())){
                nodeVO.setChecked(true);
            }

            setChecked((List<PermissionRespNodeVO>) nodeVO.getChildren(),checkList);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateReqVO vo) {
        //????????????????????????
        SysRole sysRole=sysRoleMapper.selectByPrimaryKey(vo.getId());
        //????????????????????????
        if(sysRole==null){
            log.error("??????id:{}?????????",vo.getId());
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        BeanUtils.copyProperties(vo,sysRole);

        sysRole.setUpdateTime(new Date());

        //??????????????????
        int update=sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if(update!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //???????????????????????????
        RolePermissionAddReqVO rolePermissionAddReqVO=new RolePermissionAddReqVO();
        rolePermissionAddReqVO.setRoleId(vo.getId());
        rolePermissionAddReqVO.setPermissionIds(vo.getPermissionIds());
        rolePermissionService.addRolePermission(rolePermissionAddReqVO);

        //?????????????????????????????????????????????token
        List<String>roleIds=new ArrayList<>();
        roleIds.add(vo.getId());
        List<String>userIds=sysUserRoleMapper.getUserIdsByRoleIds(roleIds);

        for(String userId:userIds){
            redisService.set(Constant.JWT_REFRESH_KEY+userId,
                    userId,tokenSetting.getAccessTokenExpireTime().toMillis(),
                    TimeUnit.MILLISECONDS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(String roleId) {
        SysRole sysRole=new SysRole();
        sysRole.setUpdateTime(new Date());
        sysRole.setId(roleId);
        sysRole.setDeleted(0);
        int i=sysRoleMapper.updateByPrimaryKeySelective(sysRole);
        if(i!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //???????????????????????????????????????
        rolePermissionService.removeByRoleId(roleId);

        //?????????????????????????????????
        List<String>roleIds=new ArrayList<>();
        roleIds.add(roleId);
        List<String>userIds=sysUserRoleMapper.getUserIdsByRoleIds(roleIds);
        //????????????????????????????????????
        userRoleService.removeUserRoleId(roleId);

        if(!userIds.isEmpty()){
            for(String userId:userIds){
                redisService.set(Constant.JWT_REFRESH_KEY+userId,
                        userId,tokenSetting.getAccessTokenExpireTime().toMillis(),
                        TimeUnit.MILLISECONDS);
            }
        }

    }


    @Override
    public List<String> getNamesByUserId(String userId) {
        List<String> roleIdsByUserId = userRoleService.getRoleIdsByUserId(userId);
        if(roleIdsByUserId.isEmpty()){
            return null;
        }
        return sysRoleMapper.selectNamesByIds(roleIdsByUserId);
    }
}
