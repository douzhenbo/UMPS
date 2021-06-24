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
        permissionRespNodeVO.setTitle("顶级菜单");
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
     * 递归方法获取子集菜单
     * @param type 若type=true，则遍历到菜单，若type=false,则遍历到按钮
     * @param all
     * @return
     */

    private List<PermissionRespNodeVO> getTree(List<SysPermission>all,boolean type){
        List<PermissionRespNodeVO>list=new ArrayList<>();
        if(all.isEmpty()||all.isEmpty()){
            return list;
        }

        for(SysPermission sysPermission:all){
            if(sysPermission.getPid().equals("0")){
                PermissionRespNodeVO permissionRespNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(sysPermission,permissionRespNodeVO);
                permissionRespNodeVO.setTitle(sysPermission.getName());
                if(type){
                    permissionRespNodeVO.setChildren(getChildrenExBtn(sysPermission.getId(), all));
                }else{
                    permissionRespNodeVO.setChildren(getChild(sysPermission.getId(),all));
                }
                list.add(permissionRespNodeVO);
            }
        }

        return list;
    }

    /**
     * 遍历到按钮
     * @param id
     * @param all
     * @return
     */

    private List<PermissionRespNodeVO> getChild(String id,List<SysPermission>all){
        List<PermissionRespNodeVO>list=new ArrayList<>();
        for(SysPermission s:all){
            if(s.getPid().equals(id)){
                PermissionRespNodeVO permissionRespNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,permissionRespNodeVO);
                permissionRespNodeVO.setTitle(s.getName());
                permissionRespNodeVO.setChildren(getChildrenExBtn(s.getId(), all));
                list.add(permissionRespNodeVO);
            }
        }
        return list;
    }

    /**
     * 遍历到菜单
     */
    private List<PermissionRespNodeVO>getChildrenExBtn(String id,List<SysPermission>all){
        List<PermissionRespNodeVO>list=new ArrayList<>();
        for(SysPermission s:all){
            if(s.getPid().equals(id)&&s.getType()!=3){
                PermissionRespNodeVO permissionRespNodeVO=new PermissionRespNodeVO();
                BeanUtils.copyProperties(s,permissionRespNodeVO);
                permissionRespNodeVO.setTitle(s.getName());
                permissionRespNodeVO.setChildren(getChildrenExBtn(s.getId(), all));
                list.add(permissionRespNodeVO);
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
     * 更新菜单操作
     */
    @Override
    public void updatePermission(PermissionUpdateReqVO vo) {
        SysPermission update=new SysPermission();

        BeanUtils.copyProperties(vo,update);

        veriform(update);

        SysPermission sysPermission=sysPermissionMapper.selectByPrimaryKey(vo.getId());

        if(sysPermission==null){
            log.info("传入的id在数据库中不存在");
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        if(!sysPermission.getPid().equals(vo.getPid())){
            //所属菜单发生了变化，要判断所属菜单是否存在子集
            if(sysPermissionMapper.selectChild(sysPermission.getId())!=null){
                throw new BusinessException(BaseResponseCode.OPERATION_MENU_PERMISSION_UPDATE);
            }
        }

        update.setUpdateTime(new Date());
        int updates=sysPermissionMapper.updateByPrimaryKeySelective(update);

        if(updates!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //判断授权标识符是否发生了变化
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


}
