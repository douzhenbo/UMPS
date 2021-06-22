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
public class UserPageVO {

    @ApiModelProperty(value = "当前第几页")
    private Integer pageNum=1;


    @ApiModelProperty(value = "每页的大小")
    private Integer pageSize=10;
}
