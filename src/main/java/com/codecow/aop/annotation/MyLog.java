package com.codecow.aop.annotation;

import java.lang.annotation.*;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/29 14:40
 **/
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyLog {
    /**
     * 用户操作那个模块
     */
    String title() default "";

    /**
     * 记录用户操作的动作
     */
    String action() default "";
}
