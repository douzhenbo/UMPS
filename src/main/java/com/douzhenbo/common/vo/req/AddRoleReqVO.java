package com.douzhenbo.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 16:12
 **/
@Data
public class AddRoleReqVO {
    @ApiModelProperty(value = "角色名称")
    @NotNull
    private String name;

    @ApiModelProperty(value = "拥有的权限id集合")
    @NotNull
    private List<String>permissionIds;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

    @ApiModelProperty(value = "角色描述")
    private String description;
}
