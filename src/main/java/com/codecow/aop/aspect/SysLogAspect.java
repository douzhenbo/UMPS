package com.codecow.aop.aspect;

import com.codecow.dao.SysLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 14:44
 **/
@Aspect
@Component
@Slf4j
public class SysLogAspect {
    @Autowired
    private SysLogMapper sysLogMapper;
    /**
     * 配置织入点(以@MyLog注解为标志)
     * 只要出现 @MyLog注解都会进入
     */
    @Pointcut("@annotation(com.codecow.aop.annotation.MyLog)")
    public void logPointCut(){

    }
}
