package cn.totime.core.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;

/**
 * @author JanYork
 * @date 2023/1/14 11:27
 * @description Redis一般数据操作通用工具类
 */
@Component
public class RedisUtils {
    /**
     * RedisTemplate对象
     */
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 设置缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存
     *
     * @param key      键
     * @param value    值
     * @param duration 过期时间
     */
    public void set(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    /**
     * 获取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 删除缓存
     *
     * @param key 键
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key     键
     * @param seconds Duration对象
     */
    public void expire(String key, Duration seconds) {
        redisTemplate.expire(key, seconds);
    }

    /**
     * 设置缓存(String类型)
     *
     * @param key   键
     * @param value 值
     */
    public void setString(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置缓存(String类型)
     *
     * @param key     键
     * @param value   值
     * @param seconds 过期时间
     */
    public void setString(String key, String value, Duration seconds) {
        redisTemplate.opsForValue().set(key, value, seconds);
    }


    /**
     * 获取缓存(String类型)
     *
     * @param key 键
     * @return 值
     */
    public String getString(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置缓存(Hash类型)
     *
     * @param key   键
     * @param field 域
     * @param value 值
     */
    public void setHash(String key, String field, Object value) {
        redisTemplate.opsForHash().put(key, field, value);
    }

    /**
     * 设置缓存(Hash类型)
     *
     * @param key     键
     * @param field   域
     * @param value   值
     * @param seconds 过期时间
     */
    public void setHash(String key, String field, Object value, Duration seconds) {
        redisTemplate.opsForHash().put(key, field, value);
        redisTemplate.expire(key, seconds);
    }

    /**
     * 获取缓存(Hash类型)
     *
     * @param key   键
     * @param field 域
     * @return 值
     */
    public Object getHash(String key, String field) {
        return redisTemplate.opsForHash().get(key, field);
    }

    /**
     * 删除缓存(Hash类型)
     *
     * @param key   键
     * @param field 域
     */
    public void deleteHash(String key, String field) {
        redisTemplate.opsForHash().delete(key, field);
    }

    /**
     * 设置缓存(List类型)
     *
     * @param key   键
     * @param value 值
     */
    public void setList(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 设置缓存(List类型)
     *
     * @param key     键
     * @param value   值
     * @param seconds 过期时间
     */
    public void setList(String key, Object value, Duration seconds) {
        redisTemplate.opsForList().rightPush(key, value);
        redisTemplate.expire(key, seconds);
    }

    /**
     * 获取缓存(List类型)
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return 值
     */
    public Object getList(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 删除缓存(List类型)
     *
     * @param key   键
     * @param count 移除多少个
     * @param value 值
     */
    public void deleteList(String key, long count, Object value) {
        redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * 设置缓存(Set类型)
     *
     * @param key   键
     * @param value 值
     */
    public void setSet(String key, Object value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 设置缓存(Set类型)
     *
     * @param key     键
     * @param value   值
     * @param seconds 过期时间
     */
    public void setSet(String key, Object value, Duration seconds) {
        redisTemplate.opsForSet().add(key, value);
        redisTemplate.expire(key, seconds);
    }

    /**
     * 获取缓存(Set类型)
     *
     * @param key 键
     * @return 值
     */
    public Set<Object> getSet(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 删除缓存(Set类型)
     *
     * @param key   键
     * @param value 值
     */
    public void deleteSet(String key, Object value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    /**
     * 设置缓存(ZSet类型)
     *
     * @param key   键
     * @param value 值
     * @param score 分数
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public void setZSet(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key, value, score);
    }

    /**
     * 设置缓存(ZSet类型)
     *
     * @param key     键
     * @param value   值
     * @param score   分数
     * @param seconds 过期时间
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public void setZSet(String key, Object value, double score, Duration seconds) {
        redisTemplate.opsForZSet().add(key, value, score);
        redisTemplate.expire(key, seconds);
    }

    /**
     * 获取缓存(ZSet类型)
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return 值
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Set<Object> getZSet(String key, long start, long end) {
        return redisTemplate.opsForZSet().range(key, start, end);
    }

    /**
     * 删除缓存(ZSet类型)
     *
     * @param key   键
     * @param value 值
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public void deleteZSet(String key, Object value) {
        redisTemplate.opsForZSet().remove(key, value);
    }

    /**
     * 获取缓存(ZSet类型)
     *
     * @param key 键
     * @return 值
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Set<Object> getZSet(String key) {
        return redisTemplate.opsForZSet().range(key, 0, -1);
    }

    /**
     * 获取缓存(ZSet类型)
     *
     * @param key 键
     * @param min 最小分数
     * @param max 最大分数
     * @return 值
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Set<Object> getZSet(String key, double min, double max) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    /**
     * 获取缓存(ZSet类型)
     *
     * @param key    键
     * @param min    最小分数
     * @param max    最大分数
     * @param offset 偏移量
     * @param count  数量
     * @return 值
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public Set<Object> getZSet(String key, double min, double max, long offset, long count) {
        return redisTemplate.opsForZSet().rangeByScore(key, min, max, offset, count);
    }
}