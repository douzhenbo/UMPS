package com.codecow.config.shiroconfig;


import com.codecow.service.RedisService;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName: ShiroCacheManager
 * TODO:类文件简单描述
 * @Author: 小霍
 * @UpdateUser: 小霍
 * @Version: 0.0.1
 */
public class ShiroCacheManager  implements CacheManager {
    @Autowired
    private RedisService redisService;
    @Override
    public <K, V> Cache<K, V> getCache(String s) throws CacheException {
        return new RedisCache<>(redisService);
    }
}
