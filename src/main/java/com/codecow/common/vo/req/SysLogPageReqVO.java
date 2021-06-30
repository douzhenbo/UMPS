package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 16:12
 **/
@Data
public class SysLogPageReqVO {
    @ApiModelProperty(value = "第几页")
    private Integer pageNum=1;
    @ApiModelProperty(value = "分页数量")
    private Integer pageSize=10;
    @ApiModelProperty(value = "用户操作动作")
    private String operation;
    @ApiModelProperty(value = "用户id")
    private String userId;
    @ApiModelProperty(value = "账号")
    private String username;
    @ApiModelProperty(value = "开始时间")
    private String startTime;
    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
