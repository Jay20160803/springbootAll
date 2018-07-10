package com.jk51.shiroTest.redisDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.hash.HashMapper;
import org.springframework.data.redis.hash.ObjectHashMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:  spring data hash mapping test
 * 作者: gaojie
 * 创建日期: 2018/7/6
 * 修改记录:
 */

@Service
public class HashMapping {

    @Resource(name="redisTemplate")
    HashOperations<String,byte[],byte[]> hashOperations;

    HashMapper<Object,byte[],byte[]> mapper = new ObjectHashMapper();

    public void writerHash(String key,Person person){

        Map<byte[], byte[]> mappedHash = mapper.toHash(person);
        hashOperations.putAll(key,mappedHash);
    }

    public Person loadHash(String key){

        Map<byte[], byte[]> loadedHash = hashOperations.entries(key);
       return (Person) mapper.fromHash(loadedHash);
    }



}
