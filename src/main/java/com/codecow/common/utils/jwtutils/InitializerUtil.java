package com.codecow.common.utils.jwtutils;

import org.springframework.stereotype.Component;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:06
 **/
@Component
public class InitializerUtil {
    public InitializerUtil(TokenSetting tokenSetting) {
        JwtTokenUtil.setJwtProperties(tokenSetting);
    }
}
