package com.codecow.service.impl;

import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.jwtutils.TokenSetting;
import com.codecow.common.vo.req.PermissionAddReqVO;
import com.codecow.common.vo.req.PermissionUpdateReqVO;
import com.codecow.common.vo.resp.PermissionRespNodeVO;
import com.codecow.dao.SysPermissionMapper;
import com.codecow.entity.SysPermission;
import com.codecow.service.IPermissionService;
import com.codecow.service.IRolePermissionService;
import com.codecow.service.IUserRoleService;
import com.codecow.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 10:52
 **/
@Service
@Slf4j
public class PermissionServiceImpl implements IPermissionService {
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private TokenSetting tokenSetting;

    @Override
    public List<SysPermission> selectAll() {
        List<SysPermission> sysPermissions = sysPermissionMapper.selectAll();
        if(!sysPermissions.isEmpty()){
            for (SysPermission sysPermission :
                    sysPermissions) {
                SysPermission parent = sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
                if(parent!=null){
                    sysPermission.setPidName(parent.getName());
                }
            }
        }
        return sysPermissions;
    }

    @Override
    public List<PermissionRespNodeVO> selectAllMenuByTree() {
        List<SysPermission>sysPermissions=sysPermissionMapper.selectAll();
        List<PermissionRespNodeVO>permissionRespNodeVOS=new ArrayList<>();
        PermissionRespNodeVO permissionRespNodeVO=new PermissionRespNodeVO();
        permissionRespNodeVO.setId("0");
        permissionRespNodeVO.setTitle("????????????");
        permissionRespNodeVO.setChildren(getTree(sysPermissions,true));
        permissionRespNodeVOS.add(permissionRespNodeVO);
        return permissionRespNodeVOS;
    }



    @Override
    public SysPermission addPermission(PermissionAddReqVO permissionAddReqVO) {
        SysPermission sysPermission=new SysPermission();
        BeanUtils.copyProperties(permissionAddReqVO,sysPermission);
        veriform(sysPermission);
        sysPermission.setId(UUID.randomUUID().toString());
        sysPermission.setCreateTime(new Date());
        int insert=sysPermissionMapper.insertSelective(sysPermission);
        if(insert!=1){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        return sysPermission;
    }


    @Override
    public List<PermissionRespNodeVO> permissionTreeList(String userId) {
        return getTree(selectAll(),true);
    }

    @Override
    public List<PermissionRespNodeVO> selectAllTree() {
        return getTree(selectAll(),false);
    }

    /**
     * ??????????????????????????????
     * @param type ???type=true???????????????????????????type=false,??????????????????
     * @param all
     * @return
     */

    private List<PermissionRespNodeVO> getTree(List<SysPermission>all,boolean type){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        if(all==null||all.isEmpty()){
            return list;
        }
        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals("0")){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(sysPermission,respNodeVO);
                respNodeVO.setTitle(sysPermission.getName());
                if(type){
                    respNodeVO.setChildren(getChildrenExBtn(sysPermission.getId(),all));
                }else {
                    respNodeVO.setChildren(getChild(sysPermission.getId(),all));
                }

                list.add(respNodeVO);
            }
        }
        return list;
    }

    /**
     * ???????????????
     * @param id
     * @param all
     * @return
     */

    private List<PermissionRespNodeVO> getChild(String id,List<SysPermission>all){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        for (SysPermission s:
                all) {
            if(s.getPid().equals(id)){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,respNodeVO);
                respNodeVO.setTitle(s.getName());
                respNodeVO.setChildren(getChild(s.getId(),all));
                list.add(respNodeVO);
            }
        }
        return list;
    }

    /**
     * ???????????????
     */
    private List<PermissionRespNodeVO>getChildrenExBtn(String id,List<SysPermission>all){
        List<PermissionRespNodeVO> list=new ArrayList<>();
        for (SysPermission s:
                all) {
            if(s.getPid().equals(id)&&s.getType()!=3){
                PermissionRespNodeVO respNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,respNodeVO);
                respNodeVO.setTitle(s.getName());
                respNodeVO.setChildren(getChildrenExBtn(s.getId(),all));
                list.add(respNodeVO);
            }
        }
        return list;
    }





    private void veriform(SysPermission sysPermission){
        SysPermission parent=sysPermissionMapper.selectByPrimaryKey(sysPermission.getPid());
        switch (sysPermission.getType()){
            case 1:
                if (parent!=null){
                    if(parent.getType()!=1){
                        throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                    }
                }else if(!sysPermission.getPid().equals("0")){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_CATALOG_ERROR);
                }
                break;
            case 2:
                if(parent==null||parent.getType()!=1){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_MENU_ERROR);
                }
                if(StringUtils.isEmpty(sysPermission.getUrl())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                break;
            case 3:
                if(parent==null||parent.getType()!=2){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_BTN_ERROR);
                }
                if(StringUtils.isEmpty(sysPermission.getPerms())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_PERMS_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getUrl())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_NOT_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getMethod())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_METHOD_NULL);
                }
                if(StringUtils.isEmpty(sysPermission.getCode())){
                    throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_URL_CODE_NULL);
                }
                break;
        }
    }


    /**
     * @author codecow
     * ??????????????????
     */
    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {
        SysPermission update=new SysPermission();

        BeanUtils.copyProperties(vo,update);

        veriform(update);

        SysPermission sysPermission=sysPermissionMapper.selectByPrimaryKey(vo.getId());

        if(sysPermission==null){
            log.info("?????????id????????????????????????");
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        if(!sysPermission.getPid().equals(vo.getPid())){
            //?????????????????????????????????????????????????????????????????????
            if(sysPermissionMapper.selectChild(sysPermission.getId())!=null){
                throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }

        update.setUpdateTime(new Date());
        int updates=sysPermissionMapper.updateByPrimaryKeySelective(update);

        if(updates!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //??????????????????????????????????????????
        if(!sysPermission.getPerms().equals(vo.getPerms())){
            List<String>roleIds=rolePermissionService.getRoleIdsByPermissionId(vo.getId());

            if(!roleIds.isEmpty()){
                List<String>userIds=userRoleService.getUserIdsByRoleIds(roleIds);

                if(!userIds.isEmpty()){
                    for(String userId:userIds){
                        redisService.set(Constant.JWT_REFRESH_KEY+userId,
                                userId,tokenSetting.getAccessTokenExpireTime().toMillis(),
                                TimeUnit.MILLISECONDS);
                    }
                }
            }
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePermission(String permissionId) {
        //???????????????????????????
        List<SysPermission>sysPermissions=sysPermissionMapper.selectChild(permissionId);

        //???????????????????????????????????????
        if(!sysPermissions.isEmpty()){
            throw new BusinessException(BaseResponseCode.ROLE_PERMISSION_RELATION);
        }

        //????????????????????????????????????
        rolePermissionService.removeByPermissionId(permissionId);

        //??????????????????
        SysPermission sysPermission=new SysPermission();
        sysPermission.setUpdateTime(new Date());
        sysPermission.setDeleted(0);
        sysPermission.setId(permissionId);
        int delete=sysPermissionMapper.updateByPrimaryKeySelective(sysPermission);
        if(delete!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //??????????????????????????????????????????
        List<String>roleIds=rolePermissionService.getRoleIdsByPermissionId(permissionId);
        if(!roleIds.isEmpty()){
            List<String>userIds=userRoleService.getUserIdsByRoleIds(roleIds);
            if(!userIds.isEmpty()){
                for(String userId:userIds){
                    redisService.set(Constant.JWT_REFRESH_KEY+userId,
                            userId,tokenSetting.getAccessTokenExpireTime().toMillis(),
                            TimeUnit.MILLISECONDS);
                }
            }
        }

    }


    @Override
    public List<String> getPermissionByUserId(String userId) {

        List<SysPermission> permissions = getPermissions(userId);
        if(permissions==null||permissions.isEmpty()){
            return null;
        }
        List<String> result=new ArrayList<>();
        for (SysPermission s:
                permissions) {
            if(!StringUtils.isEmpty(s.getPerms())){
                result.add(s.getPerms());
            }
        }
        return result;
    }

    @Override
    public List<SysPermission> getPermissions(String userId) {
        List<String> roleIdsByUserId = userRoleService.getRoleIdsByUserId(userId);
        if(roleIdsByUserId.isEmpty()){
            return null;
        }
        List<String> permissionIdsByRoleIds = rolePermissionService.getPermissionIdsByRoleIds(roleIdsByUserId);
        if (permissionIdsByRoleIds.isEmpty()){
            return null;
        }
        List<SysPermission> result=sysPermissionMapper.selectByIds(permissionIdsByRoleIds);
        return result;
    }
}
