package com.codecow.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 15:01
 **/

@Data
public class DeptRespNodeVO {
    @ApiModelProperty(value = "部门id")
    private String id;

    @ApiModelProperty(value = "部门名称")
    private String title;

    @ApiModelProperty(value = "部门父级id")
    private String pid;

    @ApiModelProperty(value = "是否展开")
    private Boolean spread=true;

    @ApiModelProperty(value = "子集节点")
    private List<?>children;
}
