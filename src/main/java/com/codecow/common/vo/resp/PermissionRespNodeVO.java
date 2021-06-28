package com.codecow.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 9:21
 **/

@Data
public class PermissionRespNodeVO {

    @ApiModelProperty(value = "菜单主键id")
    private String id;

    @ApiModelProperty(value = "跳转地址")
    private String url;

    @ApiModelProperty(value = "菜单权限名称")
    private String title;

    @ApiModelProperty(value = "子集集合")
    private List<?>children;

    @ApiModelProperty(value = "是否展开")
    private Boolean spread=true;

    @ApiModelProperty(value = "节点是否选中")
    private Boolean checked=false;
}
