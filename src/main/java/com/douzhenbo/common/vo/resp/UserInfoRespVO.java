package com.douzhenbo.common.vo.resp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/17 16:51
 **/
@Data
public class UserInfoRespVO {
    @ApiModelProperty(value = "id")
    private String userId;
}
