package net.totime.mail.annotation;

import net.totime.mail.enums.LimitType;

import java.lang.annotation.*;

/**
 * @author JanYork
 * @date 2023/3/8 13:48
 * @description 限流器注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimiter {
    /**
     * 限流key
     */
    String key() default "d:rate_limit:";

    /**
     * 限流时间,单位秒
     */
    int time() default 60;

    /**
     * 限流次数
     */
    int count() default 50;

    /**
     * 限流类型
     */
    LimitType limitType() default LimitType.IP;
}