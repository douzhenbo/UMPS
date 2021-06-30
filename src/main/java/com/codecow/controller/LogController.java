package com.codecow.controller;

import com.codecow.aop.annotation.MyLog;
import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.SysLogPageReqVO;
import com.codecow.common.vo.resp.PageVO;
import com.codecow.entity.SysLog;
import com.codecow.service.ILogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 16:21
 **/
@RestController
@Api(tags = "日志管理模块")
@RequestMapping("/log")
public class LogController {
    @Autowired
    private ILogService logService;


    @PostMapping("/getLogs")
    @ApiOperation(value = "获取日志信息")
    public DataResult<PageVO<SysLog>> pageInfo(@RequestBody SysLogPageReqVO vo){
        PageVO<SysLog> sysLogPageVO = logService.pageInfo(vo);
        DataResult<PageVO<SysLog>> result= DataResult.success();
        result.setData(sysLogPageVO);
        return result;
    }
}
