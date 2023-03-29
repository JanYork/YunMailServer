package net.totime.mail.aspect;

import lombok.extern.slf4j.Slf4j;
import net.totime.mail.annotation.RateLimiter;
import net.totime.mail.enums.LimitType;
import net.totime.mail.exception.RateLimiterException;
import net.totime.mail.util.IpUtils;
import net.totime.mail.util.ServletUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author JanYork
 * @date 2023/3/8 14:50
 * @description 限流切面
 */
@Aspect
@Component
@Slf4j
public class RateLimiterAspect {
    @Resource
    private RedisTemplate<Object, Object> redisTemplate;
    @Resource
    private RedisScript<Long> limitScript;

    /**
     * 限流
     *
     * @param point       切点
     * @param rateLimiter 限流注解
     */
    @Before("@annotation(rateLimiter)")
    public void doBefore(JoinPoint point, RateLimiter rateLimiter) {
        int time = rateLimiter.time();
        int count = rateLimiter.count();
        String combineKey = getCombineKey(rateLimiter, point);
        List<Object> keys = Collections.singletonList(combineKey);
        Long number = redisTemplate.execute(limitScript, keys, count, time);
        if (number == null || number.intValue() > count) {
            int expire = Objects.requireNonNull(redisTemplate.getExpire(combineKey)).intValue();
            throw new RateLimiterException(500,"访问过于频繁，请稍等" + expire + "秒再试");
        }
    }

    /**
     * 获取组合key
     *
     * @param rateLimiter 限流注解
     * @param point       切点
     * @return 组合key
     */
    public String getCombineKey(RateLimiter rateLimiter, JoinPoint point) {
        StringBuilder stringBuffer = new StringBuilder(rateLimiter.key());
        if (rateLimiter.limitType() == LimitType.IP) {
            stringBuffer.append(IpUtils.getIpAddr(ServletUtils.getRequestAttributes().getRequest())).append(":");
        }
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        stringBuffer.append(targetClass.getName()).append(":").append(method.getName());
        return stringBuffer.toString();
    }
}