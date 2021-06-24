package com.codecow.common.constants;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:07
 **/
public class Constant {
    /**
     * Contants 加入 用户名 key 常量
     * 用户名称 key
     */
    public static final String JWT_USER_NAME="jwt-user-name-key";

    /**
     * 角色信息key
     */
    public static final String ROLES_INFOS_KEY="roles-infos-key";

    /**
     * 权限信息key
     */
    public static final String PERMISSIONS_INFOS_KEY="permissions-infos-key";

    /**
     *  业务访问token
     */
    public static final String ACCESS_TOKEN="authorization";


    /**
     * 主动去刷新 token key(适用场景 比如修改了用户的角色/权限去刷新token)
     */
    public static final String JWT_REFRESH_KEY="jwt-refresh-key_";

    /**
     * 标记用户是否已经被锁定
     */
    public static final String ACCOUNT_LOCK_KEY="account-lock-key_";
    /**
     * 标记用户是否已经删除
     */
    public static final String DELETED_USER_KEY="deleted-user-key_";

    /**
     * 部门编码key
     */
    public static final String DEPT_CODE_KEY="dept-code-key_";
    /**
     * 用户权鉴缓存 key
     */
    public static final String IDENTIFY_CACHE_KEY="shiro-cache:com.douzhenbo.shiroconfig.CustomRealm.authorizationCache:";


}

