package com.douzhenbo.common.exceptions.code;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:36
 **/
public enum BaseResponseCode implements ResponseCodeInterface{
    /**
     * 这个要和前段约定好
     *code=0：服务器已成功处理了请求。 通常，这表示服务器提供了请求的网页。
     *code=4010001：（授权异常） 请求要求身份验证。 客户端需要跳转到登录页面重新登录
     *code=4010002：(凭证过期) 客户端请求刷新凭证接口
     *code=4030001：没有权限禁止访问
     *code=400xxxx：系统主动抛出的业务异常
     *code=5000001：系统异常
     *
     */
    SUCCESS(0,"操作成功"),
    SYSTEM_ERROR(5000001,"系统异常请稍后再试"),
    DATA_ERROR(4000001,"传入的数据异常"),
    VALIDATOR_ERROR(4000002,"参数校验异常"),
    ACCOUNT_ERROR(4000003,"该账号不存在，请移步注册"),
    ACCOUNT_LOCK_TIP(4000004,"该账号已经被锁定，请联系平台运营人员"),
    ACCOUNT_PASSWORD_ERROR(4000005,"账号密码不正确，请重新登录"),
    TOKEN_NOT_NULL(4010001,"认证token不能为空，请重新登录获取"),
    TOKEN_ERROR(4010001,"Token 认证失败，请重新登录获取最新的token"),
    ACCOUNT_LOCK(4010001,"该账号被锁定,请联系系统管理员"),
    ACCOUNT_HAS_DELETED_ERROR(4010001,"该账号已被删除，请联系系统管理员"),
    TOKEN_PAST_DUE(4010002,"token失效,请刷新token"),
    NOT_PERMISSION(4030001,"没有权限访问该资源"),

    ;

    BaseResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 响应状态码
     */
    private final int code;

    /**
     * 响应提示
     * @return
     */
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}

