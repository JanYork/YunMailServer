/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.domain.open;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import net.totime.mail.enums.SmsTemplate;
import net.totime.mail.sms.tencent.SmsRequestBuild;
import net.totime.mail.sms.tencent.TencentSmsUtil;
import net.totime.mail.util.RedisUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
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
    @Resource
    private TencentSmsUtil sut;
    private static final String CODE = "code:";
    private static final Long CODE_TIME = 120L;
    private static final Integer CODE_LENGTH = 6;
    private static final String CODE_TIME_OUT = "2";

    /**
     * 获取并缓存验证码
     *
     * @param phone 手机号
     */
    public void cacheCode(String phone) {
        String code = createCode(CODE_LENGTH);
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(new String[]{phone})
                .templateId(SmsTemplate.REGISTER)
                .params(new String[]{code, CODE_TIME_OUT})
                .build();
        sut.sendSms(build);
        rut.set(CODE + phone, code, CODE_TIME);
    }

    /**
     * 获取并缓存验证码
     *
     * @param phone    手机号
     * @param template 模板
     */
    public void cacheCode(String phone, SmsTemplate template) {
        String code = createCode(CODE_LENGTH);
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(new String[]{phone})
                .templateId(template)
                .params(new String[]{code, CODE_TIME_OUT})
                .build();
        sut.sendSms(build);
        rut.set(CODE + phone, code, CODE_TIME);
    }

    /**
     * 获取验证码
     *
     * @param phone 手机号
     * @return 验证码
     */
    public String getCode(String phone) {
        return rut.get(CODE + phone).toString();
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

    /**
     * 验证验证码
     *
     * @param phone 手机号
     * @param code  验证码
     * @return {@link Boolean} 是否正确
     */
    public Boolean verifyCode(String phone, String code) {
        Optional<Object> optional = Optional.ofNullable(rut.get(CODE + phone));
        if (optional.isEmpty()) {
            return false;
        }
        boolean e = code.equals(optional.get().toString());
        if (e) {
            rut.delete(CODE + phone);
        }
        return e;
    }
}
