package com.douzhenbo.common.utils.jwtutils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @author codecow
 * @version 1.0  UPMS
 * @date 2021/6/16 10:04
 **/
@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class TokenSetting {
    private String secretKey;
    private Duration accessTokenExpireTime;
    private Duration refreshTokenExpireTime;
    private Duration refreshTokenExpireAppTime;
    private String issuer;
}

