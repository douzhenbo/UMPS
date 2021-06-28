package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/28 12:44
 **/
@Data
public class RoleUpdateReqVO {
    @ApiModelProperty(value = "角色id")
    private String id;

    @ApiModelProperty(value = "角色名称")
    private String name;

    @ApiModelProperty(value = "角色信息描述")
    private String description;

    @ApiModelProperty(value = "角色状态（1：正常 0：弃用）")
    private Integer status;

    @ApiModelProperty(value = "角色所拥有的菜单权限")
    private List<String>permissions;
}
