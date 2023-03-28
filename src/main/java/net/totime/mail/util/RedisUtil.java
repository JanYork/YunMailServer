package net.totime.mail.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

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

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        rt.opsForValue().set(key, value);
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     */
    public void set(String key, Object value, long time) {
        rt.opsForValue().set(key, value, time);
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     * @param unit  时间单位
     */
    public void set(String key, Object value, long time, TimeUnit unit) {
        rt.opsForValue().set(key, value, time, unit);
    }

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     * @param time  过期时间
     * @param unit  时间单位
     */
    public void set(String key, String value, long time, TimeUnit unit) {
        rt.opsForValue().set(key, value, time, unit);
    }


    /**
     * 获取缓存
     *
     * @param key 键
     * @return 缓存值
     */
    public Object get(String key) {
        return rt.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void del(String key) {
        rt.delete(key);
    }
}