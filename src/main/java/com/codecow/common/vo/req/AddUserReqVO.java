package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/23 10:11
 **/
@Data
public class AddUserReqVO {
    @ApiModelProperty(value = "账户名称")
    @NotBlank(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "用户密码")
    @NotBlank(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "创建来源(1.web 2.android 3.ios )")
    private Integer createWhere;

    @ApiModelProperty(value = "所属机构")
    @NotBlank(message = "所属机构不能为空")
    private String deptId;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 )")
    private Integer status;

}
