package com.jk51.shiroTest.config.redis;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 版权所有(C) 2017 上海银路投资管理有限公司
 * 描述:
 * 作者: gaojie
 * 创建日期: 2018/7/4
 * 修改记录:
 */

@Configuration
@EnableTransactionManagement
public class RedisConfig {



    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        JedisConnectionFactory factory = new JedisConnectionFactory();
        factory.setHostName("172.20.10.192");
        factory.setPort(6379);
        factory.setDatabase(1);
        return factory;
    }

    /**
     * Redis Sentinel Support
     * */
    @Bean
    public RedisConnectionFactory sendinelJedisConnectionFactory(){
        RedisSentinelConfiguration setinelConfig = new RedisSentinelConfiguration()
                .master("mymaster")
                .sentinel("172.20.10.192",26379)
                .sentinel("172.20.10.193",26379);

        JedisConnectionFactory  factory = new JedisConnectionFactory(setinelConfig);
        factory.setDatabase(2);
        return factory;
    }

    /**
     * RedisTemplate
     * */
    @Primary
    @Bean
    public RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(sendinelJedisConnectionFactory());
        redisTemplate.setEnableDefaultSerializer(false);
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setKeySerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        redisTemplate.setEnableTransactionSupport(true);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(){

        StringRedisTemplate result = new StringRedisTemplate();
        result.setConnectionFactory(sendinelJedisConnectionFactory());
        return result;
    }

}
