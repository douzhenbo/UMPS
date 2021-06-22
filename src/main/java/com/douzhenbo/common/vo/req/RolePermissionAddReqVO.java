package com.douzhenbo.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 16:32
 **/
@Data
public class RolePermissionAddReqVO {

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "菜单权限集合")
    private List<String>permissionIds;


}
