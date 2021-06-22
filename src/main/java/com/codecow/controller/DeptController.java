package com.codecow.controller;

import com.codecow.common.utils.DataResult;
import com.codecow.entity.SysDept;
import com.codecow.service.IDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/22 13:57
 **/

@RestController
@RequestMapping("/dept")
@Api(tags = "部门管理接口")
public class DeptController {
    @Autowired
    private IDeptService deptService;

    @ApiOperation(value = "获取所有的部门信息接口")
    @GetMapping("getDeptList")
    public DataResult<List<SysDept>> getDeptList(){
        DataResult dataResult=DataResult.success();
        dataResult.setData(deptService.selectAll());
        return dataResult;
    }

}
