package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/23 15:47
 **/
@Data
public class UserOwnRoleReqVO {
    @ApiModelProperty(value = "用户id")
    @NotBlank(message = "用户id不能为空")
    private String userId;

    @ApiModelProperty(value = "赋予用户的角色id集合")
    private List<String>roleIds;
}
