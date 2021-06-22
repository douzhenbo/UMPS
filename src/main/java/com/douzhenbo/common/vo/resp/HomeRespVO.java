package com.douzhenbo.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/17 16:49
 **/
@Data
public class HomeRespVO {
    @ApiModelProperty(value = "用户信息")
    private UserInfoRespVO userInfoRespVO;

    @ApiModelProperty(value = "首页菜单导航数据")
    private List<PermissionRespNodeVO>menus;
}
