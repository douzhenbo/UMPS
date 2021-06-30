package com.codecow.service.impl;

import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.jwtutils.JwtTokenUtil;
import com.codecow.common.utils.jwtutils.TokenSetting;
import com.codecow.common.utils.pageutils.PageUtil;
import com.codecow.common.utils.passwordutils.PasswordUtils;
import com.codecow.common.vo.req.*;
import com.codecow.common.vo.resp.LoginRespVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.common.vo.resp.UserOwnRoleRespVO;
import com.codecow.dao.SysDeptMapper;
import com.codecow.dao.SysRoleMapper;
import com.codecow.dao.SysUserMapper;
import com.codecow.dao.SysUserRoleMapper;
import com.codecow.entity.SysDept;
import com.codecow.entity.SysRole;
import com.codecow.entity.SysUser;
import com.codecow.entity.SysUserRole;
import com.codecow.service.IRoleService;
import com.codecow.service.IUserRoleService;
import com.codecow.service.IUserService;
import com.codecow.service.RedisService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.TimeUnit;

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

    @Autowired
    private SysDeptMapper sysDeptMapper;

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private TokenSetting tokenSetting;

    @Autowired
    private RedisService redisService;

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
     * @Author:      codecow
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
    public PageVO<SysUser> pageInfo(UserPageReqVO vo) {
        PageHelper.startPage(vo.getPageNum(),vo.getPageSize());
        List<SysUser>sysUsers=sysUserMapper.selectAll(vo);
        for(SysUser s:sysUsers){
            SysDept dept=sysDeptMapper.selectByPrimaryKey(s.getDeptId());
            if(dept!=null){
                s.setDeptName(dept.getName());
            }
        }
        return PageUtil.getPageVo(sysUsers);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUser addUser(AddUserReqVO vo) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setCreateTime(new Date());
        sysUser.setSalt(PasswordUtils.getSalt());
        String encode=PasswordUtils.encode(sysUser.getPassword(),sysUser.getSalt());
        sysUser.setPassword(encode);
        sysUser.setId(UUID.randomUUID().toString());
        int insert=sysUserMapper.insertSelective(sysUser);
        if(insert!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }
        return null;
    }

    @Override
    public UserOwnRoleRespVO getUserOwnRole(String userId) {
        UserOwnRoleRespVO userOwnRoleRespVO=new UserOwnRoleRespVO();
        userOwnRoleRespVO.setAllRole(roleService.selectAll());
        userOwnRoleRespVO.setRoleIds(userRoleService.getRoleIdsByUserId(userId));
        return userOwnRoleRespVO;
    }

    @Override
    public void setUserOwnRole(UserOwnRoleReqVO vo) {
        userRoleService.giveUserRoles(vo);
        /**
         * 标记用户要主动去刷新token
         */
        redisService.set(Constant.JWT_REFRESH_KEY+vo.getUserId(),vo.getUserId(),
                tokenSetting.getAccessTokenExpireTime().toMillis(), TimeUnit.MILLISECONDS);
    }


    @Override
    public String refreshToken(String refreshToken) {
        //校验这个刷新token是否有效
        //它是否过期
        //它是否被加如了黑名
        if(!JwtTokenUtil.validateToken(refreshToken)||redisService.hasKey(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken)){
            throw new BusinessException(BaseResponseCode.TOKEN_ERROR);
        }

        String userId=JwtTokenUtil.getUserId(refreshToken);
        String username=JwtTokenUtil.getUserName(refreshToken);
        Map<String,Object>cliams=new HashMap<>();
        cliams.put(Constant.JWT_USER_NAME,username);
        cliams.put(Constant.ROLES_INFOS_KEY,getRoleByUserId(userId));
        cliams.put(Constant.PERMISSIONS_INFOS_KEY,getPermissionByUserId(userId));
        String newAccessToken=JwtTokenUtil.getAccessToken(userId,cliams);
        return newAccessToken;
        //校验刷新token是否黑名单
    }


    @Override
    public void updateUserInfo(UserUpdateReqVO vo,String operationId) {
        SysUser user=new SysUser();
        BeanUtils.copyProperties(vo,user);
        user.setUpdateId(operationId);
        user.setUpdateTime(new Date());

        //判断密码是否为空
        if(StringUtils.isEmpty(user.getPassword())){
            user.setPassword(null);
        }else{
            String salt=PasswordUtils.getSalt();
            String encode=PasswordUtils.encode(user.getPassword(),salt);
            user.setSalt(salt);
            user.setPassword(encode);
        }

        //判断是否更新成功，若没有更新成功便抛出异常
        int update=sysUserMapper.updateByPrimaryKeySelective(user);
        if(update!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //如果被禁用则标记该用户（方便自动刷新token）
        if(vo.getStatus()==2){
            redisService.set(Constant.ACCOUNT_LOCK_KEY+vo.getId(),vo.getId());
        }else{
            redisService.delete(Constant.ACCOUNT_LOCK_KEY+vo.getId());
        }
    }


    /**
     * 删除&批量删除用户
     */
    @Override
    public void deleteUsers(List<String> list, String operationId) {
        SysUser sysUser=new SysUser();
        sysUser.setUpdateId(operationId);
        sysUser.setUpdateTime(new Date());
        int delete=sysUserMapper.deleteUsers(sysUser,list);
        if(delete==0){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

        //标记被删除用户的token
        for(String userId:list){
            redisService.set(Constant.DELETED_USER_KEY+userId
                    ,userId,
                    tokenSetting.getRefreshTokenExpireAppTime().toMillis(),
                    TimeUnit.MILLISECONDS);
        }

    }



    @Override
    public void logout(String accessToken, String refreshToken) {
        if(StringUtils.isEmpty(accessToken)||StringUtils.isEmpty(refreshToken)){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }

        Subject subject = SecurityUtils.getSubject();
        log.info("subject.getPrincipals()={}",subject.getPrincipals());
        if (subject.isAuthenticated()) {
            subject.logout();
        }
        String userId=JwtTokenUtil.getUserId(accessToken);
        /**
         * 把token 加入黑名单 禁止再登录
         */

        redisService.set(Constant.JWT_ACCESS_TOKEN_BLACKLIST+accessToken,
                userId,
                JwtTokenUtil.getRemainingTime(accessToken),
                TimeUnit.MILLISECONDS);
        /**
         * 把 refreshToken 加入黑名单 禁止再拿来刷新token
         */

        redisService.set(Constant.JWT_REFRESH_TOKEN_BLACKLIST+refreshToken,
                userId,
                JwtTokenUtil.getRemainingTime(refreshToken),
                TimeUnit.MILLISECONDS);
    }


    @Override
    public SysUser detailUserInfo(String userId) {
        return sysUserMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void userUpdateDetailInfo(UserUpdateDetailReqVO vo, String userId) {
        SysUser sysUser=new SysUser();
        BeanUtils.copyProperties(vo,sysUser);
        sysUser.setId(userId);
        sysUser.setUpdateTime(new Date());
        sysUser.setUpdateId(userId);
        int count= sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (count!=1){
            throw new BusinessException(BaseResponseCode.OPERATION_ERROR);
        }

    }
}
