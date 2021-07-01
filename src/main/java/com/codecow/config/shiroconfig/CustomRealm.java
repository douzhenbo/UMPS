package com.codecow.config.shiroconfig;


import com.codecow.common.constants.Constant;
import com.codecow.common.utils.jwtutils.JwtTokenUtil;
import com.codecow.service.IPermissionService;
import com.codecow.service.IRoleService;
import com.codecow.service.RedisService;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: CustomRealm
 * TODO:类文件简单描述
 * @Author: codecow
 * @UpdateUser: codecow
 * @Version: 0.0.1
 */
@Slf4j
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private IRoleService roleService;
    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private RedisService redisService;
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomUsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String accessToken= (String) principals.getPrimaryPrincipal();
        Claims claimsFromToken = JwtTokenUtil.getClaimsFromToken(accessToken);
        SimpleAuthorizationInfo info=new SimpleAuthorizationInfo();
        String userId=JwtTokenUtil.getUserId(accessToken);
        log.info("userId={}",userId);
        if(redisService.hasKey(Constant.JWT_REFRESH_KEY+userId)&&redisService.getExpire(Constant.JWT_REFRESH_KEY+userId, TimeUnit.MILLISECONDS)>JwtTokenUtil.getRemainingTime(accessToken)){
            List<String> roles=roleService.getNamesByUserId(userId);
            if(roles!=null&&!roles.isEmpty()){
                info.addRoles(roles);
            }
            List<String> permissionByUserId = permissionService.getPermissionByUserId(userId);
            if(permissionByUserId!=null&&!permissionByUserId.isEmpty()){
                info.addStringPermissions(permissionByUserId);
            }

        }else {
            if(claimsFromToken.get(Constant.PERMISSIONS_INFOS_KEY)!=null){
                info.addStringPermissions((Collection<String>) claimsFromToken.get(Constant.PERMISSIONS_INFOS_KEY));
            }
            if(claimsFromToken.get(Constant.ROLES_INFOS_KEY)!=null){
                info.addRoles((Collection<String>) claimsFromToken.get(Constant.ROLES_INFOS_KEY));
            }
        }

        return info;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken customUsernamePasswordToken= (CustomUsernamePasswordToken) authenticationToken;
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(customUsernamePasswordToken.getPrincipal(),customUsernamePasswordToken.getCredentials(),CustomRealm.class.getName());
        return info;
    }
}
