/*
 * Copyright (c) 2023. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package net.totime.mail.controller.user;

import cn.dev33.satoken.stp.StpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.totime.mail.enums.KeyType;
import net.totime.mail.exception.GloballyUniversalException;
import net.totime.mail.response.ApiResponse;
import net.totime.mail.service.UserService;
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
@Api(tags = "[用户]短信验证码")
@RequestMapping("/user/sms")
public class SmsCodeApi {
    @Resource
    private SmsVerifyUtil smsVerifyUtil;
    @Resource
    private UserService userService;

    /**
     * 获取短信验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/get")
    @ApiOperation("获取短信验证码")
    public ApiResponse<Boolean> getSmsCode(String phone) {
        return smsVerifyUtil.code(phone, KeyType.NORMAL, 120L, 2);
    }

    /**
     * 获取修改密码验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/getByUpdatePassword")
    @ApiOperation("获取修改密码验证码")
    public ApiResponse<Boolean> getSmsCodeByUpdatePassword(String phone) {
        return smsVerifyUtil.codeByChangePwd(phone, 120L, 2);
    }

    /**
     * 获取绑定手机号验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/getByBindPhone")
    @ApiOperation("获取绑定手机号验证码")
    public ApiResponse<Boolean> getSmsCodeByBindPhone(String phone) {
        if (userService.getById(StpUtil.getLoginIdAsLong()).getPhone() != null) {
            return ApiResponse.fail(false).message("已绑定手机号");
        }
        return smsVerifyUtil.codeByBindPhone(phone, 120L, 2);
    }

    /**
     * 获取改绑手机号验证码
     *
     * @param phone 手机号
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/getByChangePhone")
    @ApiOperation("获取改绑手机号验证码")
    public ApiResponse<Boolean> getSmsCodeByChangePhone(String phone) {
        if (userService.getById(StpUtil.getLoginIdAsLong()).getPhone() == null) {
            return ApiResponse.fail(false).message("未绑定手机号");
        }
        return smsVerifyUtil.codeByChangeBindPhone(phone, 120L, 2);
    }

    /**
     * 获取短信验证码
     *
     * @return {@link ApiResponse}<{@link Boolean}>
     */
    @GetMapping("/getByUser")
    @ApiOperation("获取短信验证码")
    public ApiResponse<Boolean> getSmsCodeByUser() {
        String phone = userService.getById(StpUtil.getLoginIdAsLong()).getPhone();
        if (phone == null) {
            throw new GloballyUniversalException(666, "请先绑定手机号");
        }
        return smsVerifyUtil.code(phone, KeyType.NORMAL, 120L, 2);
    }
}
