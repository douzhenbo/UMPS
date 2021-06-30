package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/30 13:13
 **/
@Data
public class UserUpdateDetailReqVO {
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "真实姓名")
    private String realName;
    @ApiModelProperty(value = "用户状态")
    private Integer status;
    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "性别")
    private Integer sex;
}
