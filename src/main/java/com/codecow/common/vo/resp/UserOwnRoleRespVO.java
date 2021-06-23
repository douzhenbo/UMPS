package com.codecow.common.vo.resp;

import com.codecow.entity.SysRole;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/23 14:50
 **/
@Data
public class UserOwnRoleRespVO {
    @ApiModelProperty(value = "所有的角色")
    private List<SysRole>allRole;

    @ApiModelProperty(value = "用户所拥有的角色id")
    private List<String>roleIds;
}
