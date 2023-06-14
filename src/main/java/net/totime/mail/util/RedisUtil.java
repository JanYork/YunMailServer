/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/27
 * @description Redis工具类
 * @since 1.0.0
 */
@Component
public class RedisUtil {
    @Resource
    private RedisTemplate<String, Object> rt;

    public void set(String key, Object value) {
        rt.opsForValue().set(key, value);
    }

    public void set(String key, Object value, Long time) {
        rt.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    public Object get(String key) {
        return rt.opsForValue().get(key);
    }

    public void delete(String key) {
        rt.delete(key);
    }

    /**
     * 计数器
     *
     * @param key  键
     * @param time 过期时间
     * @return {@link Long}
     */
    public Long incr(String key, Long time) {
        Long count = rt.opsForValue().increment(key);
        Assert.notNull(count, "计数器异常");
        if (count == 1) {
            rt.expire(key, time, TimeUnit.SECONDS);
        }
        return count;
    }
}
