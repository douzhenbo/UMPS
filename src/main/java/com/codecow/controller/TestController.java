package com.codecow.controller;

import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.DataResult;
import com.codecow.common.vo.req.TestReqVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/15 17:01
 **/
@RestController
@Api(tags = "测试接口模块",description = "主要是为了提供测试接口用")
@RequestMapping("/test")
public class TestController {
    @GetMapping("/index")
    @ApiOperation(value = "引导页接口")
    public String testResult(){
        return "Hello World";
    }

    @GetMapping("/home")
    @ApiOperation(value = "测试DataResult接口")
    public DataResult<String> getHome(){
        int i=1/0;
        DataResult<String> result=DataResult.success("哈哈哈哈测试成功 欢迎来到ERP SYSTEM");
        return result;
    }


    @GetMapping("/business/error")
    @ApiOperation(value = "测试主动抛出业务异常接口")
    public DataResult<String> testBusinessError(@RequestParam String type){
        if(!(type.equals("1")||type.equals("2")||type.equals("3"))){
            throw new BusinessException(BaseResponseCode.DATA_ERROR);
        }
        DataResult<String> result=new DataResult(0,type);
        return result;
    }


    @PostMapping("/valid/error")
    @ApiOperation(value = "测试Validator抛出业务异常接口")
    public DataResult testValid(@RequestBody @Valid TestReqVO vo){
        DataResult result=DataResult.success();
        return result;
    }

}
