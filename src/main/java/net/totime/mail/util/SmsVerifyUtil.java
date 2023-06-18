/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.util;

import com.tencentcloudapi.sms.v20210111.models.SendSmsRequest;
import com.tencentcloudapi.sms.v20210111.models.SendSmsResponse;
import net.totime.mail.enums.KeyType;
import net.totime.mail.enums.SmsTemplate;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.sms.tencent.SmsRequestBuild;
import net.totime.mail.sms.tencent.TencentSmsUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 短信验证码工具类
 * @since 1.0.0
 */
@Component
public class SmsVerifyUtil {
    @Resource
    private TencentSmsUtil sut;
    private static final String SMS_OK = "Ok";
    @Resource
    private RedisUtil rut;

    /**
     * 发送短信验证码
     *
     * @param phone   手机号
     * @param keyType 缓存key头
     * @param time    有效时间
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    public ApiResponse<Boolean> code(String phone, KeyType keyType, Long cacheTime, Integer time) {
        String regex = "^1[3-9]\\d{9}$";
        if (!phone.matches(regex)) {
            return ApiResponse.fail(false).message("手机号格式不正确");
        }
        Long incr = rut.incr(phone, 60L);
        if (incr > 5) {
            return ApiResponse.fail(false).message("获取过于频繁");
        }
        String[] phoneNumbers = {phone};
        String code = CodeUtil.generateCode(6);
        SendSmsRequest build = SmsRequestBuild.builder()
                .phoneNumber(phoneNumbers)
                .templateId(SmsTemplate.NORMAL)
                .params(new String[]{
                        code, String.valueOf(time)
                })
                .build();
        SendSmsResponse response = sut.sendSms(build);
        if (!SMS_OK.equals(response.getSendStatusSet()[0].getCode())) {
            return ApiResponse.fail(false).message("发送失败");
        }
        rut.set(keyType.getKey() + phone, code, cacheTime);
        return ApiResponse.ok(true).message("发送成功");
    }

    /**
     * 验证短信验证码
     *
     * @param phone   电话
     * @param keyType 密钥类型
     * @param code    验证码
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    public boolean verify(String phone, KeyType keyType, String code) {
        String s = (String) rut.get(keyType.getKey() + phone);
        if (StringUtils.isEmpty(s)) {
            return false;
        }
        return s.equals(code);
    }
}
