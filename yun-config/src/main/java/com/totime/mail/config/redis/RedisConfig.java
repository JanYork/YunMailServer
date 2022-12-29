package com.totime.mail.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author JanYork
 * @date 2022/12/29 15:08
 * @description Redis配置类
 */
@Configuration
public class RedisConfig {
    /**
     * Redis序列化配置
     *
     * @param redisTemplate RedisTemplate对象
     * @return RedisTemplate对象
     */
    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisTemplate<?, ?> redisTemplate) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());
        return redisTemplate;
    }
}