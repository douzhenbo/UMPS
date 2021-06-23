package com.codecow.common.vo.req;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 13:01
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageReqVO {
    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum=1;

    @ApiModelProperty(value = "每页的大小")
    private Integer pageSize=10;

    @ApiModelProperty(value = "用户id")
    private String userId;

    @ApiModelProperty(value = "用户名称")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "账户状态(1.正常 2.锁定 ")
    private Integer status;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

}
