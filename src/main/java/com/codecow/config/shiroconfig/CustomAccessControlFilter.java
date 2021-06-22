package com.codecow.config.shiroconfig;

import com.alibaba.fastjson.JSON;
import com.codecow.common.constants.Constant;
import com.codecow.common.exceptions.BusinessException;
import com.codecow.common.exceptions.code.BaseResponseCode;
import com.codecow.common.utils.DataResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.http.MediaType;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 14:03
 **/

@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {
    /**
     * 如果返回true,就会流转到下一个链式调用
     * 如果返回false,就会流转到onAccessDenied方法进行处理
     *
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }


    /**
     * 如果返回true，就会流转到下一个链式调用
     * 如果返回false,不会流转到下一个链式调用
     *
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        log.info("request接口地址：{}", request.getRequestURI());
        log.info("request请求方式：{}", request.getMethod());
        String accessToken = request.getHeader(Constant.ACCESS_TOKEN);
        //主体提交认证
        try {
            if (accessToken == null) {
                throw new BusinessException(BaseResponseCode.TOKEN_NOT_NULL);
            }
            CustomUsernamePasswordToken customUsernamePasswordToken = new CustomUsernamePasswordToken(accessToken);
            getSubject(servletRequest, servletResponse).login(customUsernamePasswordToken);
        } catch (BusinessException e){
            cumstomResponse(servletResponse,e.getCode(),e.getMsg());
            return false;
        } catch (AuthenticationException e) {
            if(e.getCause() instanceof BusinessException){
                BusinessException businessException=(BusinessException)e.getCause();
                cumstomResponse(servletResponse,businessException.getCode(),businessException.getMsg());
            }else{
                cumstomResponse(servletResponse,BaseResponseCode.TOKEN_ERROR.getCode(),BaseResponseCode.TOKEN_ERROR.getMsg());
            }
            return false;
        }catch (Exception e){
           cumstomResponse(servletResponse,BaseResponseCode.SYSTEM_ERROR.getCode(), BaseResponseCode.SYSTEM_ERROR.getMsg());
           return  false;
        }

        return true;
    }


    /**
     * 自定义响应前端方法
     * @param response
     * @param code
     * @param message
     */

    private void cumstomResponse(ServletResponse response, Integer code,String message){
        try {
            DataResult result=DataResult.getResult(code,message);
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setCharacterEncoding("UTF-8");
            String str= JSON.toJSONString(result);
            OutputStream outputStream=response.getOutputStream();
            outputStream.write(str.getBytes("UTF-8"));
        } catch (IOException e) {
            log.error("customResponse...ERROR:{}",e);
        }
    }

}
