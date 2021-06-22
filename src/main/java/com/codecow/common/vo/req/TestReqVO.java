package com.codecow.common.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 9:55
 **/
@Data
public class TestReqVO {
    @ApiModelProperty(value = "名称")
    @NotBlank(message = "名称不能为空")
    private String name;
    @NotNull(message = "age 不能为空")
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @NotEmpty(message = "id 集合不能为空")
    @ApiModelProperty(value = "id集合")
    private List<String> ids;
}

