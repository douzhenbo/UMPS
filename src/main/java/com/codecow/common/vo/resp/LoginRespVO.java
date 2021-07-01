package com.codecow.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:12
 **/
@Data
public class LoginRespVO {
    @ApiModelProperty(value = "正常的业务token")
    private String accessToken;
    @ApiModelProperty(value = "刷新token")
    private String refreshToken;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "账号")
    private String username;
}
