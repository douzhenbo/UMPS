package com.douzhenbo.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/17 16:51
 **/
@Data
public class UserInfoRespVO {
    @ApiModelProperty(value = "用户uid")
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "部门id")
    private String deptId;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "email")
    private String email;

    @ApiModelProperty(value = "phone")
    private String phone;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "账户状态")
    private Integer status;

    @ApiModelProperty(value = "账户创建日期")
    private Date createTime;

    @ApiModelProperty(value = "phone")
    private Date updateTime;

}
