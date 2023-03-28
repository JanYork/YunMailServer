package com.totime.mail.domain;

import com.totime.mail.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/03/27
 * @description 全局验证服务实现
 * @since 1.0.0
 */
@Service
public class VerifyService {
    @Resource
    private RedisUtil rut;
    private static final String CODE = "code:";
    private static final Integer CODE_TIME = 120;
    private static final Integer CODE_LENGTH = 6;

    /**
     * 获取并缓存验证码
     *
     * @param phone 手机号
     */
    public void cacheCode(String phone) {
        rut.set(CODE+phone, this.createCode(CODE_LENGTH), CODE_TIME, TimeUnit.SECONDS);
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String getCode(String phone) {
        return rut.get(CODE+phone).toString();
    }

    /**
     * 创建验证码
     *
     * @param length 长度
     * @return {@link String} 验证码
     */
    public String createCode(Integer length) {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < length; i++) {
            code.append((int) (Math.random() * 10));
        }
        return code.toString();
    }
}