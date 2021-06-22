package com.douzhenbo.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.Date;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/21 13:30
 **/
@Data
public class RolePageReqVO {
    @ApiModelProperty(value = "第几页")
    private Integer pageNum=1;

    @ApiModelProperty(value = "当前页的数量")
    private Integer pageSize=10;

    @ApiModelProperty(value = "角色id")
    private String roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "创建时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "角色状态")
    private Integer status;

}
