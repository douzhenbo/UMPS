package com.codecow.config.shiroconfig;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 13:58
 **/

public class CustomUsernamePasswordToken extends UsernamePasswordToken {
    private String jwtToken;

    public CustomUsernamePasswordToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    @Override
    public Object getPrincipal() {
        return jwtToken;
    }

    @Override
    public Object getCredentials() {
        return jwtToken;
    }
}
