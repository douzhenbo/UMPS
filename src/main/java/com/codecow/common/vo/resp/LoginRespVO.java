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
    @ApiModelProperty(value = "业务访问token")
    private String accessToken;
    @ApiModelProperty(value = "业务token刷新凭证")
    private String refreshToken;
    @ApiModelProperty(value = "用户id")
    private String userId;
}
