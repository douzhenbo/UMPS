package com.douzhenbo.service.impl;

import com.douzhenbo.common.constants.Constant;
import com.douzhenbo.common.exceptions.BusinessException;
import com.douzhenbo.common.exceptions.code.BaseResponseCode;
import com.douzhenbo.common.utils.jwtutils.JwtTokenUtil;
import com.douzhenbo.common.utils.pageutils.PageUtil;
import com.douzhenbo.common.utils.passwordutils.PasswordUtils;
import com.douzhenbo.common.vo.req.LoginReqVO;
import com.douzhenbo.common.vo.req.UserPageVO;
import com.douzhenbo.common.vo.resp.LoginRespVO;
import com.douzhenbo.common.vo.resp.PageVO;
import com.douzhenbo.dao.SysUserMapper;
import com.douzhenbo.entity.SysUser;
import com.douzhenbo.service.IUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:15
 **/
@Slf4j
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public LoginRespVO login(LoginReqVO vo) {
        SysUser sysUser = sysUserMapper.selectByUsername(vo.getUsername());
        System.out.println(sysUser);
        if(sysUser==null){
            throw new BusinessException(BaseResponseCode.ACCOUNT_ERROR);
        }
        if(sysUser.getStatus()==2){
            throw new BusinessException(BaseResponseCode.ACCOUNT_LOCK_TIP);
        }
        if(!PasswordUtils.matches(sysUser.getSalt(),vo.getPassword(),sysUser.getPassword())){
            throw new BusinessException(BaseResponseCode.ACCOUNT_PASSWORD_ERROR);
        }
        Map<String,Object> claims=new HashMap<>();
        claims.put(Constant.JWT_USER_NAME,sysUser.getUsername());
        claims.put(Constant.ROLES_INFOS_KEY,getRoleByUserId(sysUser.getId()));
        claims.put(Constant.PERMISSIONS_INFOS_KEY,getPermissionByUserId(sysUser.getId()));
        String accessToken= JwtTokenUtil.getAccessToken(sysUser.getId(),claims);
        System.out.println("accessToken:"+accessToken);
        log.info("accessToken={}",accessToken);
        Map<String,Object> refreshTokenClaims=new HashMap<>();
        refreshTokenClaims.put(Constant.JWT_USER_NAME,sysUser.getUsername());
        String refreshToken=null;
        refreshToken=JwtTokenUtil.getRefreshToken(sysUser.getId(),refreshTokenClaims);
        log.info("refreshToken={}",refreshToken);
        LoginRespVO loginRespVO=new LoginRespVO();
        loginRespVO.setUserId(sysUser.getId());
        loginRespVO.setRefreshToken(refreshToken);
        loginRespVO.setAccessToken(accessToken);
        return loginRespVO;
    }
    /**
     * 用过用户id查询拥有的角色信息
     * @Author:      小霍
     * @UpdateUser:
     * @Version:     0.0.1
     * @param userId
     * @return       java.util.List<java.lang.String>
     * @throws
     */
    private List<String> getRoleByUserId(String userId){
        List<String> list=new ArrayList<>();
        if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
            list.add("admin");
        }else {
            list.add("dev");
        }
        return list;
    }

    private List<String> getPermissionByUserId(String userId){
        List<String> list=new ArrayList<>();
        if(userId.equals("9a26f5f1-cbd2-473d-82db-1d6dcf4598f8")){
            list.add("sys:user:add");
            list.add("sys:user:update");
            list.add("sys:user:delete");
            list.add("sys:user:list");
        }else {
            list.add("sys:user:add");
        }
        return list;
    }



    @Override
    public PageVO<SysUser> pageInfo(UserPageVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysUser>sysUsers=sysUserMapper.selectAll();
        return PageUtil.getPageVo(sysUsers);
    }
}
