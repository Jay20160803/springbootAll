package com.jk51.shiroTest.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */
public class ShiroRedisCacheManager extends AbstractCacheManager {

    private RedisTemplate<byte[],byte[]> shiroRedisTemplate;


    public ShiroRedisCacheManager(RedisTemplate<byte[], byte[]> shiroRedisTemplate) {
        this.shiroRedisTemplate = shiroRedisTemplate;
    }

    @Override
    protected Cache createCache(String name) throws CacheException {
        return new ShiroRedisCache<byte[], byte[]>(shiroRedisTemplate, name);
    }
}
