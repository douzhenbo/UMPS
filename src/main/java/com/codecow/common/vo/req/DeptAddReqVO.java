package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 16:13
 **/

@Data
public class DeptAddReqVO {
    @ApiModelProperty(value = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    private String name;

    @ApiModelProperty(value = "父级id,一级为0")
    @NotBlank(message = "父级pid为空")
    private String pid;

    @ApiModelProperty(value = "部门经理名称")
    private String managerName;

    @ApiModelProperty(value = "部门经理电话")
    private String phone;

    @ApiModelProperty(value = "机构状态：（1：正常；0:禁用）")
    private Integer status;
}
