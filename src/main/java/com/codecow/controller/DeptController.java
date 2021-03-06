package com.codecow.controller;

import com.codecow.aop.annotation.MyLog;
import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.DeptAddReqVO;
import com.codecow.common.vo.req.DeptUpdateReqVO;
import com.codecow.common.vo.resp.DeptRespNodeVO;
import com.codecow.entity.SysDept;
import com.codecow.service.IDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    @MyLog(title = "部门管理",action = "获取所有的部门信息接口")
    @RequiresPermissions("sys:dept:list")
    public DataResult<List<SysDept>> getDeptList(){
        DataResult dataResult=DataResult.success();
        dataResult.setData(deptService.selectAll());
        return dataResult;
    }

    @ApiOperation(value = "获取部门树-添加部门时使用-添加用户时使用-编辑部门时使用")
    @GetMapping("/getDeptTree")
    @MyLog(title = "部门管理",action = "获取部门树-添加部门时使用-添加用户时使用-编辑部门时使用")
    @RequiresPermissions(value = {"sys:user:update","sys:user:add","sys:dept:add","sys:dept:update"},logical = Logical.OR)
    public DataResult<List<DeptRespNodeVO>>getDeptTree(@RequestParam(required = false)String deptId){
        DataResult result=DataResult.success();
        result.setData(deptService.getDeptTreeList(deptId));
        return result;
    }

    @ApiOperation(value = "新增部门")
    @PostMapping("/addDept")
    @MyLog(title = "部门管理",action = "新增部门")
    @RequiresPermissions("sys:dept:add")
    public DataResult addDept(@RequestBody @Valid DeptAddReqVO vo){
        return DataResult.success(deptService.addDept(vo));
    }

    @ApiOperation(value = "更新部门信息")
    @PutMapping("/updateDept")
    @MyLog(title = "部门管理",action = "更新部门信息")
    @RequiresPermissions("sys:dept:update")
    public DataResult updateDept(@RequestBody @Valid DeptUpdateReqVO vo){
        deptService.updateDept(vo);
        return DataResult.success();
    }

}
