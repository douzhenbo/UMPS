package com.douzhenbo.controller;

import com.douzhenbo.common.constants.Constant;
import com.douzhenbo.common.utils.DataResult;
import com.douzhenbo.common.utils.jwtutils.JwtTokenUtil;
import com.douzhenbo.common.vo.resp.HomeRespVO;
import com.douzhenbo.service.impl.HomeServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/18 9:45
 **/

@RestController
@Api(tags = "首页模块")
public class HomeController {
    @Autowired
    private HomeServiceImpl homeService;

    @ApiOperation(value = "获取首页信息（用户，菜单（权限），部门）")
    @GetMapping("/home")
    public DataResult<HomeRespVO> getHome(HttpServletRequest request){
        String accessToken=request.getHeader(Constant.ACCESS_TOKEN);
        String userId= JwtTokenUtil.getUserId(accessToken);
        DataResult<HomeRespVO> result=DataResult.success();
        result.setData(homeService.getHome(userId));
        return result;
    }
}
