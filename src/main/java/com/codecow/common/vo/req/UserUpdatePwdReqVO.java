package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/30 13:45
 **/
@Data
public class UserUpdatePwdReqVO {
    @ApiModelProperty(value = "旧密码")
    @NotBlank(message = "旧密码不能为空")
    private String oldPwd;

    @ApiModelProperty(value = "新密码")
    @NotBlank(message = "新密码不能为空")
    private String newPwd;
}
