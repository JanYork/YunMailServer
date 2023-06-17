/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import io.swagger.annotations.ApiModel;
import net.totime.mail.enums.KeyType;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.util.SmsVerifyUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author JanYork
 * @version 1.0.0
 * @date 2023/06/17
 * @description 短信验证码
 * @since 1.0.0
 */
@RestController
@ApiModel("短信验证码(需登录)")
@RequestMapping("/api/sms")
public class SmsCodeApi {
    @Resource
    private SmsVerifyUtil smsVerifyUtil;

    /**
     * 获取短信验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/get")
    public ApiResponse<Boolean> getSmsCode(String phone) {
        return smsVerifyUtil.code(phone, KeyType.NORMAL, 120);
    }
}
