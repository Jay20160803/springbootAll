package com.jk51.shiroTest.config.shiro;

import com.jk51.shiroTest.util.SerializeUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;
import java.util.Set;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */
public class ShiroRedisCache<K,V> implements Cache<K,V> {


    private RedisTemplate<byte[],V> shiroRedisTemplate;
    private String prefix = "shiro_redis:";

    public ShiroRedisCache(RedisTemplate<byte[], V> shiroRedisTemplate) {
        this.shiroRedisTemplate = shiroRedisTemplate;
    }

    public ShiroRedisCache(RedisTemplate<byte[], V> shiroRedisTemplate, String prefix) {
        this.shiroRedisTemplate = shiroRedisTemplate;
        this.prefix = prefix;
    }

    @Override
    public V get(K k) throws CacheException {

        if(valifyIsNull(k)){
            return null;
        }

        byte[] bytes = getByteKey(k);
        return shiroRedisTemplate.opsForValue().get(bytes);
    }

    @Override
    public V put(K k, V v) throws CacheException {
        if (valifyIsNull(k,v)) {
            return null;
        }

        byte[] bkey = getByteKey(k);

        shiroRedisTemplate.opsForValue().set(bkey, v);
        return v;
    }

    @Override
    public V remove(K k) throws CacheException {
        if(valifyIsNull(k)){
            return null;
        }

        byte[] bkey = getByteKey(k);
        V value = shiroRedisTemplate.opsForValue().get(bkey);
        shiroRedisTemplate.delete(bkey);
        return value;
    }

    @Override
    public void clear() throws CacheException {

        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException();

    }

    @Override
    public Set<K> keys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    private boolean valifyIsNull(Object ... args){

        for(Object o:args){
            if(o == null){
                return true;
            }
        }

        return false;
    }

    private byte[] getByteKey(K key){

        if(key instanceof String){
            String preKey = this.prefix + key;
            return preKey.getBytes();
        }

        return SerializeUtils.serialize(key);
    }
}
